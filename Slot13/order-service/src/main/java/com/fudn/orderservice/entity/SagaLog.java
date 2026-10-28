package com.fudn.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "saga_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagaLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String step;      // e.g.: CREATE_ORDER, RESERVE_INVENTORY, PROCESS_PAYMENT, COMPENSATION
    private String result;    // SUCCESS / FAILED
    @Column(length = 1000)
    private String message;
    private LocalDateTime time;

    public SagaLog(Long orderId, String step, String result, String message) {
        this.orderId = orderId;
        this.step = step;
        this.result = result;
        this.message = message;
        this.time = LocalDateTime.now();
    }
}
