package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.NotBlank;

public record AccountSubtypeRequest(
        @NotBlank String code,
        @NotBlank String baseType,
        @NotBlank String name,
        String description,
        String status
) {}
