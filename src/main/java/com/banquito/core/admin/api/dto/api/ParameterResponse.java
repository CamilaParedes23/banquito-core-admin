package com.banquito.core.admin.api.dto.api;

public record ParameterResponse(
        String code,
        String name,
        String value,
        String dataType,
        String description,
        String status
) {}
