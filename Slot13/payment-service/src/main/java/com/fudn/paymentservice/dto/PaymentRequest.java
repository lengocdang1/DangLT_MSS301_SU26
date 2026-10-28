package com.fudn.paymentservice.dto;

public record PaymentRequest(Long orderId, String customerId, double amount) {
}
