package com.fudn.orderservice.dto;

public record PaymentRequest(Long orderId, String customerId, double amount) {
}
