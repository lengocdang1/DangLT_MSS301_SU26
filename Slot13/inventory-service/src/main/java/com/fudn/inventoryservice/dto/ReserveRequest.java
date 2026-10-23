package com.fudn.inventoryservice.dto;

import lombok.Data;

/**
 * Request body cho POST /api/inventory/reserve
 */
@Data
public class ReserveRequest {
    private String orderId;
    private String productId;
    private int quantity;
}
