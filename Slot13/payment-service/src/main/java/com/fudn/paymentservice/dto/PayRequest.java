package com.fudn.paymentservice.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO-4: Request body cho POST /api/payments/pay
 */
@Data
public class PayRequest {
    private String orderId;
    private String customerId;
    private BigDecimal amount;
}
