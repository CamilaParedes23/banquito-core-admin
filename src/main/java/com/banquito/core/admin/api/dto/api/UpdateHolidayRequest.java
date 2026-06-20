package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateHolidayRequest(
        @NotBlank @Size(max = 120) String name,
        Boolean weekend
) {}
