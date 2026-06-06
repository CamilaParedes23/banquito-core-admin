package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ParameterRequest(
        @NotBlank @Size(max = 60) String code,
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 300) String value,
        @NotBlank String dataType,
        @Size(max = 500) String description,
        String status
) {}
