package com.fudn.paymentservice.service;

import com.fudn.paymentservice.dto.PayRequest;
import com.fudn.paymentservice.model.Payment;
import com.fudn.paymentservice.model.PaymentStatus;
import com.fudn.paymentservice.model.Wallet;
import com.fudn.paymentservice.repository.PaymentRepository;
import com.fudn.paymentservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * TODO-4: PaymentService xu ly pay va refund voi @Transactional (SQL Server).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final WalletRepository walletRepository;
    private final PaymentRepository paymentRepository;

    // ─────────────────────────────────────────────────────────────
    // PAY  (POST /api/payments/pay)
    // ─────────────────────────────────────────────────────────────

    /**
     * TODO-4: Tru tien vi cho mot order.
     * - Idempotent: neu orderId da co payment PAID -> tra ve ngay (khong tru tien lan 2).
     * - 404 NOT FOUND        : customerId khong ton tai (vi chua duoc tao).
     * - 402 PAYMENT_REQUIRED : so du khong du.
     * - 200 OK               : tru tien thanh cong, luu Payment(PAID).
     */
    @Transactional
    public Payment pay(PayRequest req) {
        // --- Idempotency check ---
        Optional<Payment> existing = paymentRepository.findByOrderId(req.getOrderId());
        if (existing.isPresent()) {
            log.info("Pay idempotent hit for orderId={}", req.getOrderId());
            return existing.get();
        }

        // --- Tim vi ---
        Wallet wallet = walletRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Khong tim thay vi cua khach hang: " + req.getCustomerId()));

        // --- Kiem tra so du ---
        if (wallet.getBalance().compareTo(req.getAmount()) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.PAYMENT_REQUIRED,
                    "So du khong du cho khach hang " + req.getCustomerId()
                    + ". So du hien tai: " + wallet.getBalance()
                    + ", Can thanh toan: " + req.getAmount());
        }

        // --- Tru tien ---
        wallet.setBalance(wallet.getBalance().subtract(req.getAmount()));
        walletRepository.save(wallet);

        // --- Luu Payment(PAID) ---
        Payment payment = new Payment(null, req.getOrderId(), req.getCustomerId(),
                req.getAmount(), PaymentStatus.PAID);
        Payment saved = paymentRepository.save(payment);

        log.info("Paid {} from customer {} for orderId={}", req.getAmount(), req.getCustomerId(), req.getOrderId());
        return saved;
    }

    // ─────────────────────────────────────────────────────────────
    // REFUND  (POST /api/payments/refund/{orderId})
    // ─────────────────────────────────────────────────────────────

    /**
     * TODO-4: Hoan tien (compensation) cho mot order.
     * - Idempotent: chi hoan tien neu payment dang PAID.
     * - Neu da REFUNDED hoac chua pay -> khong loi, khong hoan tien them.
     */
    @Transactional
    public void refund(String orderId) {
        Optional<Payment> optPayment =
                paymentRepository.findByOrderIdAndStatus(orderId, PaymentStatus.PAID);

        if (optPayment.isEmpty()) {
            log.info("Refund no-op for orderId={} (not found or already refunded)", orderId);
            return;
        }

        Payment payment = optPayment.get();

        // --- Cong lai tien ---
        Wallet wallet = walletRepository.findById(payment.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Khong tim thay vi khi hoan tien: " + payment.getCustomerId()));

        wallet.setBalance(wallet.getBalance().add(payment.getAmount()));
        walletRepository.save(wallet);

        // --- Doi trang thai PAID -> REFUNDED ---
        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);

        log.info("Refunded {} to customer {} for orderId={}",
                payment.getAmount(), payment.getCustomerId(), orderId);
    }
}
