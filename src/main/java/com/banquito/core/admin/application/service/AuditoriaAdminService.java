package com.banquito.core.admin.application.service;

import com.banquito.core.admin.domain.enums.ResultadoAuditoriaAdminEnum;
import com.banquito.core.admin.domain.model.AuditoriaAdminEvento;
import com.banquito.core.admin.domain.repository.AuditoriaAdminEventoRepository;
import com.banquito.core.admin.shared.tracing.CorrelationIdHolder;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaAdminService {
    private final AuditoriaAdminEventoRepository repository;

    public AuditoriaAdminService(AuditoriaAdminEventoRepository repository) {
        this.repository = repository;
    }

    public void registrar(String uuidUsuario, String accion, String entidad, String entidadId, ResultadoAuditoriaAdminEnum resultado, String detalleJson) {
        repository.save(AuditoriaAdminEvento.crear(CorrelationIdHolder.get(), uuidUsuario, accion, entidad, entidadId, resultado, detalleJson));
    }
}
