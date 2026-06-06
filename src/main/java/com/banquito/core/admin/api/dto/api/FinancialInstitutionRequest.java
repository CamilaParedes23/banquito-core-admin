package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.NotBlank;

public record FinancialInstitutionRequest(
        @NotBlank String routingCode,
        @NotBlank String name,
        Boolean banquito,
        String status
) {}
