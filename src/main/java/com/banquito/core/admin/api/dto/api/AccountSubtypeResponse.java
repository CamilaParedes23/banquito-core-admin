package com.banquito.core.admin.api.dto.api;

import java.math.BigDecimal;
import java.util.List;

public record AccountSubtypeResponse(
        String code,
        String baseType,
        String name,
        String description,
        List<String> allowedCustomerTypes,
        List<String> allowedPurposes,
        Boolean supportsMassPayments,
        Boolean supportsFavoritePaymentAccount,
        BigDecimal minimumOpeningBalance,
        String status
) {}
