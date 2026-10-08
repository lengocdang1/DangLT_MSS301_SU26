package com.fudn.customerservice.dto;

import com.fudn.customerservice.model.Customer;
import com.fudn.customerservice.model.CustomerStatus;

public record CustomerResponse(
        Long id,
        String email,
        String fullName,
        String phone,
        String role,
        CustomerStatus status
) {
    public static CustomerResponse from(Customer c) {
        return new CustomerResponse(c.getId(), c.getEmail(), c.getFullName(),
                c.getPhone(), c.getRole(), c.getStatus());
    }
}
