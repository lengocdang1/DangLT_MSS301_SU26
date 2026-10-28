package com.fudn.paymentservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Payment {
    @Id
    private Long orderId;
    private String customerId;
    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
