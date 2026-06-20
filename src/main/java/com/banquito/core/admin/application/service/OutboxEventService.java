package com.banquito.core.admin.application.service;

import com.banquito.core.admin.domain.model.OutboxEvent;
import com.banquito.core.admin.domain.repository.OutboxEventRepository;
import com.banquito.core.admin.shared.tracing.CorrelationIdHolder;
import org.springframework.stereotype.Service;

@Service
public class OutboxEventService {
    private final OutboxEventRepository repository;

    public OutboxEventService(OutboxEventRepository repository) { this.repository = repository; }

    public void registrar(String tipoEvento, String agregadoTipo, String agregadoId, String payloadJson) {
        repository.save(OutboxEvent.crear(CorrelationIdHolder.get(), tipoEvento, agregadoTipo, agregadoId, payloadJson));
    }

    public long contarPendientes() {
        return repository.countByEstado(com.banquito.core.admin.domain.enums.EstadoOutboxEventEnum.PENDIENTE);
    }
}
