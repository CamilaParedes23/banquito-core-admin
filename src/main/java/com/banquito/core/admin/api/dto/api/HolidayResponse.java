package com.banquito.core.admin.api.dto.api;

import java.time.LocalDate;

public record HolidayResponse(
        LocalDate holidayDate,
        String name,
        Boolean weekend,
        String status
) {}
