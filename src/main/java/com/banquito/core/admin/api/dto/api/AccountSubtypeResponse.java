package com.banquito.core.admin.api.dto.api;

public record AccountSubtypeResponse(
        String code,
        String baseType,
        String name,
        String description,
        String status
) {}
