package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.NotBlank;

public record TransactionSubtypeRequest(
        @NotBlank String code,
        @NotBlank String name,
        @NotBlank String baseMovementType,
        String description,
        String status
) {}
