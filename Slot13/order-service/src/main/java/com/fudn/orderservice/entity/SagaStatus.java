package com.fudn.orderservice.entity;

// Đúng 6 trạng thái của Slide 27
public enum SagaStatus {
    PENDING,
    EXECUTING,
    COMPLETED,
    FAILED,
    COMPENSATING,
    COMPENSATED
}
