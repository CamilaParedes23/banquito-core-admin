package com.banquito.core.admin.api.controller;

import com.banquito.core.admin.api.dto.api.AuditoriaEventoListResponse;
import com.banquito.core.admin.api.dto.api.AuditoriaEventoResponse;
import com.banquito.core.admin.application.service.AuditoriaAdminService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping({"/api/v1/audit", "/api/v1/admin/audit"})
public class AuditController {

    private final AuditoriaAdminService auditoriaService;

    public AuditController(AuditoriaAdminService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping("/events")
    public AuditoriaEventoListResponse listAuditEvents(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta,
            @RequestParam(required = false) String modulo,
            @RequestParam(required = false) String accion,
            @RequestParam(required = false) String entidad,
            @RequestParam(required = false) String entidadId,
            @RequestParam(required = false) String resultado,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return auditoriaService.listarEventos(fechaDesde, fechaHasta, modulo, accion, entidad, entidadId, resultado, page, size);
    }

    @GetMapping("/events/recent")
    public List<AuditoriaEventoResponse> listRecentAuditEvents(@RequestParam(required = false) Integer limit) {
        return auditoriaService.listarRecientes(limit);
    }

    @GetMapping("/events/{id}")
    public AuditoriaEventoResponse getAuditEvent(@PathVariable Long id) {
        return auditoriaService.obtenerEvento(id);
    }
}
