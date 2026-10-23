package com.fudn.paymentservice.controller;

import com.fudn.paymentservice.dto.PayRequest;
import com.fudn.paymentservice.model.Payment;
import com.fudn.paymentservice.model.Wallet;
import com.fudn.paymentservice.repository.WalletRepository;
import com.fudn.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller cho payment-service.
 * Base path: /api/payments
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final WalletRepository walletRepository;

    // GET /api/payments/wallets – xem so du vi (tien ich debug/test)
    @GetMapping("/wallets")
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    /**
     * TODO-4: POST /api/payments/pay
     * Body: { orderId, customerId, amount }
     *
     * Response:
     * - 200 OK               – thanh toan thanh cong (hoac idempotent call)
     * - 404 NOT FOUND        – customerId khong ton tai
     * - 402 PAYMENT_REQUIRED – so du khong du
     */
    @PostMapping("/pay")
    public ResponseEntity<Payment> pay(@RequestBody PayRequest request) {
        Payment payment = paymentService.pay(request);
        return ResponseEntity.ok(payment);
    }

    /**
     * TODO-4: POST /api/payments/refund/{orderId}
     * - 200 OK – hoan tien thanh cong hoac no-op (idempotent)
     * Goi nhieu lan cung khong cong tien them.
     */
    @PostMapping("/refund/{orderId}")
    public ResponseEntity<Void> refund(@PathVariable String orderId) {
        paymentService.refund(orderId);
        return ResponseEntity.ok().build();
    }
}
