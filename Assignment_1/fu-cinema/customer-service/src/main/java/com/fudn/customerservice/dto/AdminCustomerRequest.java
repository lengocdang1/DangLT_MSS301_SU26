package com.fudn.customerservice.dto;

import com.fudn.customerservice.model.CustomerStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminCustomerRequest(
        @NotBlank @Email String email,
        @NotBlank String fullName,
        String phone,
        CustomerStatus status
) {}
