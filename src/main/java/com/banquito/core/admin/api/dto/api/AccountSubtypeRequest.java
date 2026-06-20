package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record AccountSubtypeRequest(
        @NotBlank @Pattern(regexp = "^[A-Z0-9_]{2,40}$") String code,
        @NotBlank @Pattern(regexp = "^(AHORROS|CORRIENTE)$") String baseType,
        @NotBlank @Size(max = 120) String name,
        @Size(max = 500) String description,
        @Size(max = 2) List<@Pattern(regexp = "^(NATURAL|JURIDICO)$") String> allowedCustomerTypes,
        @Size(max = 4) List<@Pattern(regexp = "^(GENERAL|OPERATIVA|NOMINA|IMPUESTOS)$") String> allowedPurposes,
        Boolean supportsMassPayments,
        Boolean supportsFavoritePaymentAccount,
        @DecimalMin(value = "0.00") BigDecimal minimumOpeningBalance,
        @Pattern(regexp = "^(ACTIVO|INACTIVO)$") String status
) {}
