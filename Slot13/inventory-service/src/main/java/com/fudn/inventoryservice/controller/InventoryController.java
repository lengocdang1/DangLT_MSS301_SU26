package com.fudn.inventoryservice.controller;

import com.fudn.inventoryservice.dto.ReserveRequest;
import com.fudn.inventoryservice.model.Product;
import com.fudn.inventoryservice.model.Reservation;
import com.fudn.inventoryservice.repository.ProductRepository;
import com.fudn.inventoryservice.repository.ReservationRepository;
import com.fudn.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;

    @PostMapping("/reserve")
    public Reservation reserve(@RequestBody ReserveRequest req) {
        return inventoryService.reserve(req);
    }

    @PostMapping("/release/{orderId}")
    public String release(@PathVariable Long orderId) {
        inventoryService.release(orderId);
        return "RELEASED";
    }

    @GetMapping("/products")
    public List<Product> products() {
        return productRepository.findAll();
    }

    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return reservationRepository.findAll();
    }
}
