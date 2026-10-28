package com.fudn.inventoryservice.dto;

public record ReserveRequest(Long orderId, String productId, int quantity) {
}
