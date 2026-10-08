package com.fudn.customerservice.controller;

import com.fudn.customerservice.dto.*;
import com.fudn.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // ===== F2: Customer endpoints =====

    @PostMapping("/api/customers/register")
    public ResponseEntity<CustomerResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.register(request));
    }

    @GetMapping("/api/customers/me")
    public ResponseEntity<CustomerResponse> getProfile(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(customerService.getProfile(userId));
    }

    @PutMapping("/api/customers/me")
    public ResponseEntity<CustomerResponse> updateProfile(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody ProfileUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateProfile(userId, request));
    }

    @PutMapping("/api/customers/me/password")
    public ResponseEntity<Void> changePassword(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody ChangePasswordRequest request) {
        customerService.changePassword(userId, request);
        return ResponseEntity.noContent().build();
    }

    // ===== F3: Admin endpoints =====

    @GetMapping("/api/admin/customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping("/api/admin/customers")
    public ResponseEntity<CustomerResponse> adminCreate(@Valid @RequestBody AdminCustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.adminCreate(request));
    }

    @PutMapping("/api/admin/customers/{id}")
    public ResponseEntity<CustomerResponse> adminUpdate(
            @PathVariable Long id,
            @Valid @RequestBody AdminCustomerRequest request) {
        return ResponseEntity.ok(customerService.adminUpdate(id, request));
    }

    @DeleteMapping("/api/admin/customers/{id}")
    public ResponseEntity<Void> adminDelete(@PathVariable Long id) {
        customerService.adminDelete(id);
        return ResponseEntity.noContent().build();
    }
}
