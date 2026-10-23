package com.fudn.orderservice.dto;

public record ReserveRequest(Long orderId, String productId, int quantity) {
}
