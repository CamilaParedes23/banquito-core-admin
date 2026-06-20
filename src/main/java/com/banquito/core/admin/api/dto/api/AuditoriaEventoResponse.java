package com.banquito.core.admin.api.dto.api;

public record AuditoriaEventoResponse(
        Long id,
        String correlationId,
        String userUuid,
        String modulo,
        String accion,
        String entidad,
        String entidadId,
        String resultado,
        String canalOrigen,
        String fechaEvento,
        String detalleJson,
        String actionCode,
        String actionName,
        String entityCode,
        String entityName,
        String resultCode,
        String resultName,
        String channelCode,
        String channelName
) {}
