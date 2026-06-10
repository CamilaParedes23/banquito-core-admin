package com.banquito.core.admin.application.service;

import com.banquito.core.admin.api.dto.api.AuditoriaEventoResponse;
import com.banquito.core.admin.domain.enums.ResultadoAuditoriaAdminEnum;
import com.banquito.core.admin.domain.model.AuditoriaAdminEvento;
import com.banquito.core.admin.domain.repository.AuditoriaAdminEventoRepository;
import com.banquito.core.admin.shared.tracing.CorrelationIdHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditoriaAdminService {
    private final AuditoriaAdminEventoRepository repository;

    public AuditoriaAdminService(AuditoriaAdminEventoRepository repository) {
        this.repository = repository;
    }

    public void registrar(String uuidUsuario, String accion, String entidad, String entidadId, ResultadoAuditoriaAdminEnum resultado, String detalleJson) {
        repository.save(AuditoriaAdminEvento.crear(CorrelationIdHolder.get(), uuidUsuario, accion, entidad, entidadId, resultado, detalleJson));
    }

    @Transactional(readOnly = true)
    public List<AuditoriaEventoResponse> listarTodos() {
        return repository.findAllByOrderByFechaEventoDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AuditoriaEventoResponse> listarRecientes() {
        return repository.findTop5ByOrderByFechaEventoDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AuditoriaEventoResponse> listarPorFechas(LocalDateTime desde, LocalDateTime hasta) {
        return repository.findByFechaEventoBetweenOrderByFechaEventoDesc(desde, hasta).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AuditoriaEventoResponse> listarPorEntidadYResultado(String entidad, ResultadoAuditoriaAdminEnum resultado) {
        return repository.findByEntidadAndResultadoOrderByFechaEventoDesc(entidad, resultado).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public long contarTotal() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    public long contarPorResultado(ResultadoAuditoriaAdminEnum resultado) {
        return repository.countByResultado(resultado);
    }

    private AuditoriaEventoResponse toResponse(AuditoriaAdminEvento evento) {
        return new AuditoriaEventoResponse(
                evento.getId(),
                evento.getModulo(),
                evento.getAccion(),
                evento.getEntidad(),
                evento.getEntidadId(),
                evento.getResultado().name(),
                evento.getFechaEvento().toString()
        );
    }
}
