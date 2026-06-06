package com.banquito.core.admin.api.dto.api;

import java.time.LocalTime;

public record OperationalWindowResponse(
        String code,
        String name,
        String operationalDomain,
        LocalTime startTime,
        LocalTime cutoffTime,
        LocalTime endTime,
        String applicableDays,
        String timezone,
        String actionAfterCutoff,
        String status
) {}
