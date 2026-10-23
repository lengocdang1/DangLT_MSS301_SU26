package com.fudn.paymentservice.controller;

import com.fudn.paymentservice.dto.ChargeRequest;
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
 * Base path: /api/payment
 */
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final WalletRepository walletRepository;

    // GET /api/payment/wallets – xem tat ca vi (tien ich debug)
    @GetMapping("/wallets")
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    /**
     * POST /api/payment/charge
     * Body: { orderId, customerId, amount }
     * - 200 OK  – charge thanh cong hoac idempotent
     * - 404 NOT FOUND – customerId khong ton tai
     * - 409 CONFLICT  – so du khong du
     */
    @PostMapping("/charge")
    public ResponseEntity<Payment> charge(@RequestBody ChargeRequest request) {
        Payment payment = paymentService.charge(request);
        return ResponseEntity.ok(payment);
    }

    /**
     * POST /api/payment/refund/{orderId}
     * - 200 OK – refund thanh cong hoac no-op (idempotent)
     */
    @PostMapping("/refund/{orderId}")
    public ResponseEntity<Void> refund(@PathVariable String orderId) {
        paymentService.refund(orderId);
        return ResponseEntity.ok().build();
    }
}
