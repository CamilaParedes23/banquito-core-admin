package com.banquito.core.admin.api.dto.api;

public record TransactionSubtypeResponse(
        String code,
        String name,
        String baseMovementType,
        String description,
        String status
) {}
