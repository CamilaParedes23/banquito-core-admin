package com.banquito.core.admin.api.dto.api;

public record FinancialInstitutionResponse(
        String routingCode,
        String name,
        Boolean banquito,
        String status
) {}
