package com.fudn.orderservice.dto;

public record CreateOrderRequest(String customerId, String productId, int quantity, double amount) {
}
