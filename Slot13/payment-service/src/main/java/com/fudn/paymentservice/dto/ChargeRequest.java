package com.fudn.paymentservice.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Request body cho POST /api/payment/charge
 */
@Data
public class ChargeRequest {
    private String orderId;
    private String customerId;
    private BigDecimal amount;
}
