package com.banquito.core.admin.application.service;

import com.banquito.core.admin.api.dto.api.AuditoriaEventoListResponse;
import com.banquito.core.admin.api.dto.api.AuditoriaEventoResponse;
import com.banquito.core.admin.domain.enums.ResultadoAuditoriaAdminEnum;
import com.banquito.core.admin.domain.model.AuditoriaAdminEvento;
import com.banquito.core.admin.domain.repository.AuditoriaAdminEventoRepository;
import com.banquito.core.admin.shared.exception.BusinessException;
import com.banquito.core.admin.shared.tracing.CorrelationIdHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditoriaAdminService {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;

    private final AuditoriaAdminEventoRepository repository;

    public AuditoriaAdminService(AuditoriaAdminEventoRepository repository) {
        this.repository = repository;
    }

    public void registrar(String uuidUsuario, String accion, String entidad, String entidadId, ResultadoAuditoriaAdminEnum resultado, String detalleJson) {
        repository.save(AuditoriaAdminEvento.crear(CorrelationIdHolder.get(), uuidUsuario, accion, entidad, entidadId, resultado, detalleJson));
    }

    @Transactional(readOnly = true)
    public AuditoriaEventoListResponse listarEventos(LocalDateTime fechaDesde,
                                                     LocalDateTime fechaHasta,
                                                     String modulo,
                                                     String accion,
                                                     String entidad,
                                                     String entidadId,
                                                     String resultado,
                                                     Integer page,
                                                     Integer size) {
        if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) {
            throw new BusinessException("ADMIN_AUDIT_DATE_RANGE_INVALID", "La fecha inicial no puede ser mayor a la fecha final", HttpStatus.BAD_REQUEST);
        }

        ResultadoAuditoriaAdminEnum resultadoEnum = parseResultado(resultado);
        Pageable pageable = pageable(page, size);
        Page<AuditoriaAdminEvento> result = repository.searchAuditEvents(
                fechaDesde,
                fechaHasta,
                blankToNull(modulo),
                blankToNull(accion),
                blankToNull(entidad),
                blankToNull(entidadId),
                resultadoEnum,
                pageable
        );

        return new AuditoriaEventoListResponse(
                result.getTotalElements(),
                result.getNumber(),
                result.getSize(),
                result.getTotalPages(),
                result.getContent().stream().map(this::toResponse).toList()
        );
    }

    @Transactional(readOnly = true)
    public List<AuditoriaEventoResponse> listarRecientes(Integer limit) {
        int safeLimit = limit == null || limit <= 0 ? 5 : Math.min(limit, 20);
        return repository.findAll(PageRequest.of(0, safeLimit, Sort.by(Sort.Direction.DESC, "fechaEvento")))
                .getContent()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AuditoriaEventoResponse obtenerEvento(Long id) {
        AuditoriaAdminEvento evento = repository.findById(id)
                .orElseThrow(() -> new BusinessException("ADMIN_AUDIT_EVENT_NOT_FOUND", "Evento de auditoría no encontrado", HttpStatus.NOT_FOUND));
        return toResponse(evento);
    }


    @Transactional(readOnly = true)
    public long contarTotal() {
        return repository.count();
    }

    private AuditoriaEventoResponse toResponse(AuditoriaAdminEvento evento) {
        return new AuditoriaEventoResponse(
                evento.getId(),
                evento.getUuidCorrelacion(),
                evento.getUuidUsuario(),
                evento.getModulo(),
                evento.getAccion(),
                evento.getEntidad(),
                evento.getEntidadId(),
                evento.getResultado() == null ? null : evento.getResultado().name(),
                "ADMIN_SERVICE",
                evento.getFechaEvento() == null ? null : evento.getFechaEvento().toString(),
                evento.getDetalleJson(),
                evento.getAccion(),
                actionName(evento.getAccion()),
                evento.getEntidad(),
                entityName(evento.getEntidad()),
                evento.getResultado() == null ? null : evento.getResultado().name(),
                resultName(evento.getResultado() == null ? null : evento.getResultado().name()),
                "ADMIN_SERVICE",
                "Servicio de administración"
        );
    }

    private String actionName(String code) {
        if (code == null) return null;
        return switch (code) {
            case "CREATE_CORE_USER" -> "Crear usuario interno";
            case "CHANGE_CORE_USER_STATUS" -> "Cambiar estado de usuario interno";
            case "CREATE_PARAMETER" -> "Crear parámetro";
            case "UPDATE_PARAMETER" -> "Actualizar parámetro";
            case "CREATE_BRANCH" -> "Crear sucursal";
            case "UPDATE_BRANCH" -> "Actualizar sucursal";
            case "CHANGE_BRANCH_STATUS" -> "Cambiar estado de sucursal";
            case "CREATE_HOLIDAY" -> "Crear feriado";
            case "CHANGE_HOLIDAY_STATUS" -> "Cambiar estado de feriado";
            case "CREATE_ACCOUNT_SUBTYPE" -> "Crear producto de cuenta";
            case "UPDATE_ACCOUNT_SUBTYPE" -> "Actualizar producto de cuenta";
            case "CREATE_TRANSACTION_SUBTYPE" -> "Crear subtipo de transacción";
            case "UPDATE_TRANSACTION_SUBTYPE" -> "Actualizar subtipo de transacción";
            default -> code.replace('_', ' ').toLowerCase();
        };
    }

    private String entityName(String code) {
        if (code == null) return null;
        return switch (code) {
            case "USUARIO_CORE" -> "Usuario interno";
            case "PARAMETRO_CORE" -> "Parámetro del Core";
            case "SUCURSAL" -> "Sucursal";
            case "FERIADO" -> "Feriado";
            case "SUBTIPO_CUENTA" -> "Producto de cuenta";
            case "SUBTIPO_TRANSACCION" -> "Subtipo de transacción";
            case "VENTANA_OPERATIVA" -> "Ventana operativa";
            case "INSTITUCION_FINANCIERA" -> "Institución financiera";
            default -> code.replace('_', ' ').toLowerCase();
        };
    }

    private String resultName(String code) {
        if (code == null) return null;
        return switch (code) {
            case "OK" -> "Exitoso";
            case "ERROR" -> "Fallido";
            case "DENEGADO" -> "Denegado";
            default -> code;
        };
    }

    private ResultadoAuditoriaAdminEnum parseResultado(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String normalized = value.trim().toUpperCase();
        if ("EXITOSO".equals(normalized)) {
            normalized = "OK";
        } else if ("FALLIDO".equals(normalized)) {
            normalized = "ERROR";
        } else if ("RECHAZADO".equals(normalized)) {
            normalized = "DENEGADO";
        }
        try {
            return ResultadoAuditoriaAdminEnum.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("ADMIN_AUDIT_RESULT_INVALID", "Resultado de auditoría inválido. Valores permitidos: OK, ERROR, DENEGADO", HttpStatus.BAD_REQUEST);
        }
    }

    private Pageable pageable(Integer page, Integer size) {
        int safePage = page == null || page < 0 ? 0 : page;
        int safeSize = size == null || size <= 0 ? DEFAULT_PAGE_SIZE : Math.min(size, MAX_PAGE_SIZE);
        return PageRequest.of(safePage, safeSize);
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
