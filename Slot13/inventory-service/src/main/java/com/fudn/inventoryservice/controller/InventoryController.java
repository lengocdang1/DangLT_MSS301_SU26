package com.fudn.inventoryservice.controller;

import com.fudn.inventoryservice.dto.ReserveRequest;
import com.fudn.inventoryservice.model.Product;
import com.fudn.inventoryservice.model.Reservation;
import com.fudn.inventoryservice.repository.ProductRepository;
import com.fudn.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller cho inventory-service.
 * Base path: /api/inventory
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductRepository productRepository;

    // ─────────────────────────────────────────────────────────────
    // TODO-1 – GET /api/inventory/products
    // ─────────────────────────────────────────────────────────────

    /**
     * Trả về danh sách tất cả sản phẩm.
     * Tiêu chí: phải trả về 2 sản phẩm P1, P2 sau khi seed.
     */
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ─────────────────────────────────────────────────────────────
    // TODO-2 – POST /api/inventory/reserve
    // ─────────────────────────────────────────────────────────────

    /**
     * Đặt giữ kho cho một order.
     * Body: { orderId, productId, quantity }
     * - 200 OK  – đặt thành công (hoặc idempotent call)
     * - 404 NOT FOUND – productId không tồn tại
     * - 409 CONFLICT  – không đủ stock
     */
    @PostMapping("/reserve")
    public ResponseEntity<Reservation> reserve(@RequestBody ReserveRequest request) {
        Reservation reservation = inventoryService.reserve(request);
        return ResponseEntity.ok(reservation);
    }

    // ─────────────────────────────────────────────────────────────
    // TODO-2 – POST /api/inventory/release/{orderId}
    // ─────────────────────────────────────────────────────────────

    /**
     * Hoàn kho (compensation / rollback) cho một order.
     * - 200 OK  – release thành công hoặc no-op (idempotent)
     * Gọi nhiều lần cũng không cộng kho thêm lần 2.
     */
    @PostMapping("/release/{orderId}")
    public ResponseEntity<Void> release(@PathVariable String orderId) {
        inventoryService.release(orderId);
        return ResponseEntity.ok().build();
    }
}
