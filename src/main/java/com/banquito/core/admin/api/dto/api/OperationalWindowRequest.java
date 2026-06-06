package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record OperationalWindowRequest(
        @NotBlank String code,
        @NotBlank String name,
        @NotBlank String operationalDomain,
        @NotNull LocalTime startTime,
        @NotNull LocalTime cutoffTime,
        @NotNull LocalTime endTime,
        String applicableDays,
        String timezone,
        @NotBlank String actionAfterCutoff,
        String status
) {}
