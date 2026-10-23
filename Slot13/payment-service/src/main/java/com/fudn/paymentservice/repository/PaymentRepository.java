package com.fudn.paymentservice.repository;

import com.fudn.paymentservice.model.Payment;
import com.fudn.paymentservice.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(String orderId);

    Optional<Payment> findByOrderIdAndStatus(String orderId, PaymentStatus status);
}
