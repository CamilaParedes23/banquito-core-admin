package com.banquito.core.admin.api.dto.api;

import java.time.LocalDate;

public record BusinessDayResponse(
        LocalDate date,
        Boolean holiday,
        Boolean weekend,
        Boolean businessDay,
        String description
) {}
