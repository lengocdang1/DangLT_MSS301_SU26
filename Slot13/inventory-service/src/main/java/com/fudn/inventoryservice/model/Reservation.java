package com.fudn.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * TODO-1: @Document cho MongoDB collection "reservations"
 * Fields: id, orderId (unique index), productId, quantity, status
 */
@Document(collection = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    private String id;

    @Indexed(unique = true)
    private String orderId;

    private String productId;

    private int quantity;

    private ReservationStatus status; // RESERVED | RELEASED
}
