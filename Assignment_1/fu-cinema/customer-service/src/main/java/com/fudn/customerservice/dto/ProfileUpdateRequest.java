package com.fudn.customerservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfileUpdateRequest(
        @NotBlank String fullName,
        String phone
) {}
