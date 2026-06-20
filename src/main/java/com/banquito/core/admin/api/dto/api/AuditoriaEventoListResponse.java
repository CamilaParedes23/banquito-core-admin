package com.banquito.core.admin.api.dto.api;

import java.util.List;

public record AuditoriaEventoListResponse(
        long total,
        int page,
        int size,
        int totalPages,
        List<AuditoriaEventoResponse> events
) {}
