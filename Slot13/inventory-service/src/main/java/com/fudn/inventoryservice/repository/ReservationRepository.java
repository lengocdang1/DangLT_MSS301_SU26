package com.fudn.inventoryservice.repository;

import com.fudn.inventoryservice.model.Reservation;
import com.fudn.inventoryservice.model.ReservationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

    Optional<Reservation> findByOrderId(String orderId);

    Optional<Reservation> findByOrderIdAndStatus(String orderId, ReservationStatus status);
}
