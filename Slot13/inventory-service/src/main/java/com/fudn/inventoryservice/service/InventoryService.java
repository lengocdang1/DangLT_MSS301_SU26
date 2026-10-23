package com.fudn.inventoryservice.service;

import com.fudn.inventoryservice.dto.ReserveRequest;
import com.fudn.inventoryservice.model.Product;
import com.fudn.inventoryservice.model.Reservation;
import com.fudn.inventoryservice.model.ReservationStatus;
import com.fudn.inventoryservice.repository.ProductRepository;
import com.fudn.inventoryservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * TODO-2: Service xử lý reserve và release với @Transactional (MongoDB replica set).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;

    // ─────────────────────────────────────────────────────────────
    // RESERVE
    // ─────────────────────────────────────────────────────────────

    /**
     * Đặt giữ hàng cho một order.
     * - Idempotent: nếu orderId đã có reservation → trả về ngay (không trừ kho lần 2).
     * - 404 nếu productId không tồn tại.
     * - 409 nếu không đủ stock.
     */
    @Transactional
    public Reservation reserve(ReserveRequest req) {
        // Idempotency check – nếu đã reserve rồi thì trả luôn
        Optional<Reservation> existing = reservationRepository.findByOrderId(req.getOrderId());
        if (existing.isPresent()) {
            log.info("Reserve idempotent hit for orderId={}", req.getOrderId());
            return existing.get();
        }

        // Tìm sản phẩm
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found: " + req.getProductId()));

        // Kiểm tra đủ hàng
        if (product.getStock() < req.getQuantity()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Insufficient stock for product " + req.getProductId()
                    + ". Available: " + product.getStock()
                    + ", Requested: " + req.getQuantity());
        }

        // Trừ kho
        product.setStock(product.getStock() - req.getQuantity());
        productRepository.save(product);

        // Lưu reservation
        Reservation reservation = new Reservation(
                null,
                req.getOrderId(),
                req.getProductId(),
                req.getQuantity(),
                ReservationStatus.RESERVED);
        Reservation saved = reservationRepository.save(reservation);

        log.info("Reserved {} units of {} for orderId={}", req.getQuantity(), req.getProductId(), req.getOrderId());
        return saved;
    }

    // ─────────────────────────────────────────────────────────────
    // RELEASE (compensation)
    // ─────────────────────────────────────────────────────────────

    /**
     * Hoàn kho (compensation) cho một order.
     * - Idempotent: chỉ hoàn kho nếu reservation đang ở trạng thái RESERVED.
     * - Nếu đã RELEASED hoặc chưa reserve → không làm gì, không lỗi.
     */
    @Transactional
    public void release(String orderId) {
        // Tìm reservation đang ở trạng thái RESERVED
        Optional<Reservation> optReservation =
                reservationRepository.findByOrderIdAndStatus(orderId, ReservationStatus.RESERVED);

        if (optReservation.isEmpty()) {
            // Idempotent: không có gì để release
            log.info("Release no-op for orderId={} (not found or already released)", orderId);
            return;
        }

        Reservation reservation = optReservation.get();

        // Cộng lại kho
        Product product = productRepository.findById(reservation.getProductId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Product not found during release: " + reservation.getProductId()));

        product.setStock(product.getStock() + reservation.getQuantity());
        productRepository.save(product);

        // Đổi trạng thái → RELEASED
        reservation.setStatus(ReservationStatus.RELEASED);
        reservationRepository.save(reservation);

        log.info("Released {} units of {} for orderId={}",
                reservation.getQuantity(), reservation.getProductId(), orderId);
    }
}
