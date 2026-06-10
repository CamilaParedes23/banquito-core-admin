package com.banquito.core.admin.api.dto.api;

public record MetricsResponse(
    long totalClientes,
    long totalCuentas,
    double saldoTotal,
    long totalEventosAuditoria,
    long sesionesActivas,
    long bloqueosFraudeHoy,
    long overridesPendientes
) {}
