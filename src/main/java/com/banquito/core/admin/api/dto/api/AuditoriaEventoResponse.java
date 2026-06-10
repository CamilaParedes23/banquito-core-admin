package com.banquito.core.admin.api.dto.api;

public record AuditoriaEventoResponse(
    Long id,
    String modulo,
    String accion,
    String entidad,
    String entidadId,
    String resultado,
    String fechaEvento
) {}
