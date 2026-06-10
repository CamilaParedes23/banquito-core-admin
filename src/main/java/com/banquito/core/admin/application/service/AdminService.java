package com.banquito.core.admin.application.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.admin.api.dto.api.AccountSubtypeRequest;
import com.banquito.core.admin.api.dto.api.AccountSubtypeResponse;
import com.banquito.core.admin.api.dto.api.BranchRequest;
import com.banquito.core.admin.api.dto.api.BranchResponse;
import com.banquito.core.admin.api.dto.api.BusinessDayResponse;
import com.banquito.core.admin.api.dto.api.ChangeStatusRequest;
import com.banquito.core.admin.api.dto.api.FinancialInstitutionRequest;
import com.banquito.core.admin.api.dto.api.FinancialInstitutionResponse;
import com.banquito.core.admin.api.dto.api.HolidayRequest;
import com.banquito.core.admin.api.dto.api.HolidayResponse;
import com.banquito.core.admin.api.dto.api.OperationalWindowRequest;
import com.banquito.core.admin.api.dto.api.OperationalWindowResponse;
import com.banquito.core.admin.api.dto.api.ParameterRequest;
import com.banquito.core.admin.api.dto.api.ParameterResponse;
import com.banquito.core.admin.api.dto.api.TransactionSubtypeRequest;
import com.banquito.core.admin.api.dto.api.TransactionSubtypeResponse;
import com.banquito.core.admin.api.dto.api.UserCoreRequest;
import com.banquito.core.admin.api.dto.api.UserCoreResponse;
import com.banquito.core.admin.domain.enums.AccionDespuesCorteEnum;
import com.banquito.core.admin.domain.enums.DominioOperativoEnum;
import com.banquito.core.admin.domain.enums.EstadoInstitucionFinancieraEnum;
import com.banquito.core.admin.domain.enums.EstadoRegistroEnum;
import com.banquito.core.admin.domain.enums.EstadoSucursalEnum;
import com.banquito.core.admin.domain.enums.EstadoUsuarioCoreEnum;
import com.banquito.core.admin.domain.enums.EstadoVentanaOperativaEnum;
import com.banquito.core.admin.domain.enums.ResultadoAuditoriaAdminEnum;
import com.banquito.core.admin.domain.enums.TipoBaseCuentaEnum;
import com.banquito.core.admin.domain.enums.TipoDatoParametroEnum;
import com.banquito.core.admin.domain.enums.TipoMovimientoBaseEnum;
import com.banquito.core.admin.domain.model.Feriado;
import com.banquito.core.admin.domain.model.InstitucionFinanciera;
import com.banquito.core.admin.domain.model.ParametroCore;
import com.banquito.core.admin.domain.model.SubtipoCuenta;
import com.banquito.core.admin.domain.model.SubtipoTransaccion;
import com.banquito.core.admin.domain.model.Sucursal;
import com.banquito.core.admin.domain.model.UsuarioCore;
import com.banquito.core.admin.domain.model.VentanaOperativa;
import com.banquito.core.admin.domain.repository.FeriadoRepository;
import com.banquito.core.admin.domain.repository.InstitucionFinancieraRepository;
import com.banquito.core.admin.domain.repository.ParametroCoreRepository;
import com.banquito.core.admin.domain.repository.SubtipoCuentaRepository;
import com.banquito.core.admin.domain.repository.SubtipoTransaccionRepository;
import com.banquito.core.admin.domain.repository.SucursalRepository;
import com.banquito.core.admin.domain.repository.UsuarioCoreRepository;
import com.banquito.core.admin.domain.repository.VentanaOperativaRepository;
import com.banquito.core.admin.shared.exception.BusinessException;

@Service
public class AdminService {

    private final SucursalRepository sucursalRepository;
    private final FeriadoRepository feriadoRepository;
    private final ParametroCoreRepository parametroRepository;
    private final VentanaOperativaRepository ventanaRepository;
    private final InstitucionFinancieraRepository institucionRepository;
    private final SubtipoCuentaRepository subtipoCuentaRepository;
    private final SubtipoTransaccionRepository subtipoTransaccionRepository;
    private final UsuarioCoreRepository usuarioCoreRepository;
    private final AdminMapper mapper;
    private final AuditoriaAdminService auditoriaService;
    private final OutboxEventService outboxEventService;

    public AdminService(SucursalRepository sucursalRepository, FeriadoRepository feriadoRepository,
                        ParametroCoreRepository parametroRepository, VentanaOperativaRepository ventanaRepository,
                        InstitucionFinancieraRepository institucionRepository,
                        SubtipoCuentaRepository subtipoCuentaRepository,
                        SubtipoTransaccionRepository subtipoTransaccionRepository,
                        UsuarioCoreRepository usuarioCoreRepository, AdminMapper mapper,
                        AuditoriaAdminService auditoriaService, OutboxEventService outboxEventService) {
        this.sucursalRepository = sucursalRepository;
        this.feriadoRepository = feriadoRepository;
        this.parametroRepository = parametroRepository;
        this.ventanaRepository = ventanaRepository;
        this.institucionRepository = institucionRepository;
        this.subtipoCuentaRepository = subtipoCuentaRepository;
        this.subtipoTransaccionRepository = subtipoTransaccionRepository;
        this.usuarioCoreRepository = usuarioCoreRepository;
        this.mapper = mapper;
        this.auditoriaService = auditoriaService;
        this.outboxEventService = outboxEventService;
    }

    @Transactional(readOnly = true)
    public List<BranchResponse> listarSucursales(String status) {
        if (status == null || status.isBlank()) return sucursalRepository.findAll().stream().map(mapper::toBranchResponse).toList();
        return sucursalRepository.findByEstadoOrderByNombreAsc(enumValue(EstadoSucursalEnum.class, status, "ADMIN_BRANCH_STATUS_INVALID")).stream().map(mapper::toBranchResponse).toList();
    }

    @Transactional(readOnly = true)
    public BranchResponse obtenerSucursal(String codigo) {
        return mapper.toBranchResponse(findSucursal(codigo));
    }

    @Transactional
    public BranchResponse crearSucursal(BranchRequest request, String actorUuid) {
        if (sucursalRepository.existsByCodigoSucursal(request.code())) {
            throw new BusinessException("ADMIN_BRANCH_DUPLICATED", "Ya existe una sucursal con el código indicado", HttpStatus.CONFLICT);
        }
        Sucursal sucursal = sucursalRepository.save(Sucursal.crear(request.code(), request.name(), request.city(), request.address()));
        auditoriaService.registrar(actorUuid, "CREATE_BRANCH", "SUCURSAL", sucursal.getCodigoSucursal(), ResultadoAuditoriaAdminEnum.OK, null);
        outboxEventService.registrar("ADMIN_BRANCH_CREATED", "SUCURSAL", sucursal.getCodigoSucursal(), "{\"code\":\"" + sucursal.getCodigoSucursal() + "\"}");
        return mapper.toBranchResponse(sucursal);
    }

    @Transactional
    public BranchResponse actualizarSucursal(String codigo, BranchRequest request, String actorUuid) {
        Sucursal sucursal = findSucursal(codigo);
        sucursal.actualizar(request.name(), request.city(), request.address());
        auditoriaService.registrar(actorUuid, "UPDATE_BRANCH", "SUCURSAL", sucursal.getCodigoSucursal(), ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toBranchResponse(sucursalRepository.save(sucursal));
    }

    @Transactional
    public BranchResponse cambiarEstadoSucursal(String codigo, ChangeStatusRequest request, String actorUuid) {
        Sucursal sucursal = findSucursal(codigo);
        sucursal.cambiarEstado(enumValue(EstadoSucursalEnum.class, request.status(), "ADMIN_BRANCH_STATUS_INVALID"));
        auditoriaService.registrar(actorUuid, "CHANGE_BRANCH_STATUS", "SUCURSAL", sucursal.getCodigoSucursal(), ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toBranchResponse(sucursalRepository.save(sucursal));
    }

    @Transactional(readOnly = true)
    public List<HolidayResponse> listarFeriados(String status) {
        if (status == null || status.isBlank()) return feriadoRepository.findAll().stream().map(mapper::toHolidayResponse).toList();
        return feriadoRepository.findByEstadoOrderByFechaFeriadoAsc(enumValue(EstadoRegistroEnum.class, status, "ADMIN_HOLIDAY_STATUS_INVALID")).stream().map(mapper::toHolidayResponse).toList();
    }

    @Transactional
    public HolidayResponse crearFeriado(HolidayRequest request, String actorUuid) {
        if (feriadoRepository.existsById(request.holidayDate())) {
            throw new BusinessException("ADMIN_HOLIDAY_DUPLICATED", "Ya existe un feriado configurado para esa fecha", HttpStatus.CONFLICT);
        }
        Feriado feriado = feriadoRepository.save(Feriado.crear(request.holidayDate(), request.name(), request.weekend()));
        auditoriaService.registrar(actorUuid, "CREATE_HOLIDAY", "FERIADO", feriado.getFechaFeriado().toString(), ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toHolidayResponse(feriado);
    }

    @Transactional
    public HolidayResponse cambiarEstadoFeriado(LocalDate fecha, ChangeStatusRequest request, String actorUuid) {
        Feriado feriado = feriadoRepository.findById(fecha).orElseThrow(() -> notFound("ADMIN_HOLIDAY_NOT_FOUND", "Feriado no encontrado"));
        feriado.cambiarEstado(enumValue(EstadoRegistroEnum.class, request.status(), "ADMIN_HOLIDAY_STATUS_INVALID"));
        auditoriaService.registrar(actorUuid, "CHANGE_HOLIDAY_STATUS", "FERIADO", fecha.toString(), ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toHolidayResponse(feriadoRepository.save(feriado));
    }

    @Transactional(readOnly = true)
    public BusinessDayResponse obtenerDiaHabil(LocalDate fecha) {
        boolean weekend = fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY;
        Feriado feriado = feriadoRepository.findById(fecha).orElse(null);
        boolean holiday = feriado != null && feriado.getEstado() == EstadoRegistroEnum.ACTIVO;
        boolean businessDay = !weekend && !holiday;
        String description = holiday ? feriado.getNombre() : (weekend ? "Fin de semana" : "Día hábil");
        return new BusinessDayResponse(fecha, holiday, weekend, businessDay, description);
    }

    @Transactional(readOnly = true)
    public BusinessDayResponse obtenerSiguienteDiaHabil(LocalDate fecha) {
        LocalDate siguiente = fecha.plusDays(1);
        BusinessDayResponse evaluacion = obtenerDiaHabil(siguiente);
        while (!evaluacion.businessDay()) {
            siguiente = siguiente.plusDays(1);
            evaluacion = obtenerDiaHabil(siguiente);
        }
        return evaluacion;
    }

    @Transactional(readOnly = true)
    public List<ParameterResponse> listarParametros(String status) {
        if (status == null || status.isBlank()) return parametroRepository.findAll().stream().map(mapper::toParameterResponse).toList();
        return parametroRepository.findByEstadoOrderByCodigoAsc(enumValue(EstadoRegistroEnum.class, status, "ADMIN_PARAMETER_STATUS_INVALID")).stream().map(mapper::toParameterResponse).toList();
    }

    @Transactional(readOnly = true)
    public ParameterResponse obtenerParametro(String codigo) { return mapper.toParameterResponse(findParametro(codigo)); }

    @Transactional
    public ParameterResponse crearParametro(ParameterRequest request, String actorUuid) {
        if (parametroRepository.existsById(request.code())) throw new BusinessException("ADMIN_PARAMETER_DUPLICATED", "Ya existe el parámetro indicado", HttpStatus.CONFLICT);
        ParametroCore parametro = parametroRepository.save(ParametroCore.crear(request.code(), request.name(), request.value(), enumValue(TipoDatoParametroEnum.class, request.dataType(), "ADMIN_PARAMETER_TYPE_INVALID"), request.description()));
        auditoriaService.registrar(actorUuid, "CREATE_PARAMETER", "PARAMETRO_CORE", parametro.getCodigo(), ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toParameterResponse(parametro);
    }

    @Transactional
    public ParameterResponse actualizarParametro(String codigo, ParameterRequest request, String actorUuid) {
        ParametroCore parametro = findParametro(codigo);
        parametro.actualizar(request.name(), request.value(), enumValue(TipoDatoParametroEnum.class, request.dataType(), "ADMIN_PARAMETER_TYPE_INVALID"), request.description(), request.status() == null ? parametro.getEstado() : enumValue(EstadoRegistroEnum.class, request.status(), "ADMIN_PARAMETER_STATUS_INVALID"));
        auditoriaService.registrar(actorUuid, "UPDATE_PARAMETER", "PARAMETRO_CORE", codigo, ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toParameterResponse(parametroRepository.save(parametro));
    }

    @Transactional(readOnly = true)
    public List<OperationalWindowResponse> listarVentanas(String domain, String status) {
        EstadoVentanaOperativaEnum estado = status == null || status.isBlank() ? EstadoVentanaOperativaEnum.ACTIVA : enumValue(EstadoVentanaOperativaEnum.class, status, "ADMIN_WINDOW_STATUS_INVALID");
        if (domain != null && !domain.isBlank()) {
            return ventanaRepository.findByDominioOperativoAndEstadoOrderByCodigoAsc(enumValue(DominioOperativoEnum.class, domain, "ADMIN_WINDOW_DOMAIN_INVALID"), estado).stream().map(mapper::toOperationalWindowResponse).toList();
        }
        return ventanaRepository.findByEstadoOrderByCodigoAsc(estado).stream().map(mapper::toOperationalWindowResponse).toList();
    }

    @Transactional(readOnly = true)
    public OperationalWindowResponse obtenerVentana(String codigo) { return mapper.toOperationalWindowResponse(findVentana(codigo)); }

    @Transactional
    public OperationalWindowResponse crearVentana(OperationalWindowRequest request, String actorUuid) {
        if (ventanaRepository.existsByCodigo(request.code())) throw new BusinessException("ADMIN_WINDOW_DUPLICATED", "Ya existe la ventana operativa indicada", HttpStatus.CONFLICT);
        VentanaOperativa ventana = ventanaRepository.save(VentanaOperativa.crear(request.code(), request.name(), enumValue(DominioOperativoEnum.class, request.operationalDomain(), "ADMIN_WINDOW_DOMAIN_INVALID"), request.startTime(), request.cutoffTime(), request.endTime(), defaultString(request.applicableDays(), "LUN,MAR,MIE,JUE,VIE"), defaultString(request.timezone(), "America/Guayaquil"), enumValue(AccionDespuesCorteEnum.class, request.actionAfterCutoff(), "ADMIN_WINDOW_ACTION_INVALID")));
        auditoriaService.registrar(actorUuid, "CREATE_OPERATIONAL_WINDOW", "VENTANA_OPERATIVA", ventana.getCodigo(), ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toOperationalWindowResponse(ventana);
    }

    @Transactional
    public OperationalWindowResponse actualizarVentana(String codigo, OperationalWindowRequest request, String actorUuid) {
        VentanaOperativa ventana = findVentana(codigo);
        ventana.actualizar(request.name(), enumValue(DominioOperativoEnum.class, request.operationalDomain(), "ADMIN_WINDOW_DOMAIN_INVALID"), request.startTime(), request.cutoffTime(), request.endTime(), defaultString(request.applicableDays(), ventana.getDiasAplica()), defaultString(request.timezone(), ventana.getTimezone()), enumValue(AccionDespuesCorteEnum.class, request.actionAfterCutoff(), "ADMIN_WINDOW_ACTION_INVALID"), request.status() == null ? ventana.getEstado() : enumValue(EstadoVentanaOperativaEnum.class, request.status(), "ADMIN_WINDOW_STATUS_INVALID"));
        auditoriaService.registrar(actorUuid, "UPDATE_OPERATIONAL_WINDOW", "VENTANA_OPERATIVA", codigo, ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toOperationalWindowResponse(ventanaRepository.save(ventana));
    }

    @Transactional(readOnly = true)
    public List<FinancialInstitutionResponse> listarInstituciones(String status) {
        if (status == null || status.isBlank()) return institucionRepository.findAll().stream().map(mapper::toFinancialInstitutionResponse).toList();
        return institucionRepository.findByEstadoOrderByNombreAsc(enumValue(EstadoInstitucionFinancieraEnum.class, status, "ADMIN_INSTITUTION_STATUS_INVALID")).stream().map(mapper::toFinancialInstitutionResponse).toList();
    }

    @Transactional(readOnly = true)
    public FinancialInstitutionResponse obtenerInstitucion(String routingCode) { return mapper.toFinancialInstitutionResponse(findInstitucion(routingCode)); }

    @Transactional
    public FinancialInstitutionResponse crearInstitucion(FinancialInstitutionRequest request, String actorUuid) {
        if (institucionRepository.existsByRoutingCode(request.routingCode())) throw new BusinessException("ADMIN_INSTITUTION_DUPLICATED", "Ya existe la institución financiera indicada", HttpStatus.CONFLICT);
        InstitucionFinanciera institucion = institucionRepository.save(InstitucionFinanciera.crear(request.routingCode(), request.name(), request.banquito()));
        auditoriaService.registrar(actorUuid, "CREATE_FINANCIAL_INSTITUTION", "INSTITUCION_FINANCIERA", institucion.getRoutingCode(), ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toFinancialInstitutionResponse(institucion);
    }

    @Transactional
    public FinancialInstitutionResponse actualizarInstitucion(String routingCode, FinancialInstitutionRequest request, String actorUuid) {
        InstitucionFinanciera institucion = findInstitucion(routingCode);
        institucion.actualizar(request.name(), request.banquito(), request.status() == null ? institucion.getEstado() : enumValue(EstadoInstitucionFinancieraEnum.class, request.status(), "ADMIN_INSTITUTION_STATUS_INVALID"));
        auditoriaService.registrar(actorUuid, "UPDATE_FINANCIAL_INSTITUTION", "INSTITUCION_FINANCIERA", routingCode, ResultadoAuditoriaAdminEnum.OK, null);
        return mapper.toFinancialInstitutionResponse(institucionRepository.save(institucion));
    }

    @Transactional(readOnly = true)
    public List<AccountSubtypeResponse> listarSubtiposCuenta(String baseType, String status) {
        EstadoRegistroEnum estado = status == null || status.isBlank() ? EstadoRegistroEnum.ACTIVO : enumValue(EstadoRegistroEnum.class, status, "ADMIN_ACCOUNT_SUBTYPE_STATUS_INVALID");
        if (baseType != null && !baseType.isBlank()) return subtipoCuentaRepository.findByTipoBaseAndEstadoOrderByNombreAsc(enumValue(TipoBaseCuentaEnum.class, baseType, "ADMIN_ACCOUNT_BASE_TYPE_INVALID"), estado).stream().map(mapper::toAccountSubtypeResponse).toList();
        return subtipoCuentaRepository.findByEstadoOrderByNombreAsc(estado).stream().map(mapper::toAccountSubtypeResponse).toList();
    }
    @Transactional(readOnly = true) public AccountSubtypeResponse obtenerSubtipoCuenta(String codigo) { return mapper.toAccountSubtypeResponse(findSubtipoCuenta(codigo)); }
    @Transactional public AccountSubtypeResponse crearSubtipoCuenta(AccountSubtypeRequest request, String actorUuid) { if (subtipoCuentaRepository.existsByCodigo(request.code())) throw new BusinessException("ADMIN_ACCOUNT_SUBTYPE_DUPLICATED", "Ya existe el subtipo de cuenta", HttpStatus.CONFLICT); SubtipoCuenta s = subtipoCuentaRepository.save(SubtipoCuenta.crear(request.code(), enumValue(TipoBaseCuentaEnum.class, request.baseType(), "ADMIN_ACCOUNT_BASE_TYPE_INVALID"), request.name(), request.description())); auditoriaService.registrar(actorUuid, "CREATE_ACCOUNT_SUBTYPE", "SUBTIPO_CUENTA", s.getCodigo(), ResultadoAuditoriaAdminEnum.OK, null); return mapper.toAccountSubtypeResponse(s); }
    @Transactional public AccountSubtypeResponse actualizarSubtipoCuenta(String codigo, AccountSubtypeRequest request, String actorUuid) { SubtipoCuenta s = findSubtipoCuenta(codigo); s.actualizar(enumValue(TipoBaseCuentaEnum.class, request.baseType(), "ADMIN_ACCOUNT_BASE_TYPE_INVALID"), request.name(), request.description(), request.status() == null ? s.getEstado() : enumValue(EstadoRegistroEnum.class, request.status(), "ADMIN_ACCOUNT_SUBTYPE_STATUS_INVALID")); auditoriaService.registrar(actorUuid, "UPDATE_ACCOUNT_SUBTYPE", "SUBTIPO_CUENTA", codigo, ResultadoAuditoriaAdminEnum.OK, null); return mapper.toAccountSubtypeResponse(subtipoCuentaRepository.save(s)); }

    @Transactional(readOnly = true)
    public List<TransactionSubtypeResponse> listarSubtiposTransaccion(String baseMovementType, String status) {
        EstadoRegistroEnum estado = status == null || status.isBlank() ? EstadoRegistroEnum.ACTIVO : enumValue(EstadoRegistroEnum.class, status, "ADMIN_TRANSACTION_SUBTYPE_STATUS_INVALID");
        if (baseMovementType != null && !baseMovementType.isBlank()) return subtipoTransaccionRepository.findByTipoMovimientoBaseAndEstadoOrderByNombreAsc(enumValue(TipoMovimientoBaseEnum.class, baseMovementType, "ADMIN_MOVEMENT_TYPE_INVALID"), estado).stream().map(mapper::toTransactionSubtypeResponse).toList();
        return subtipoTransaccionRepository.findByEstadoOrderByNombreAsc(estado).stream().map(mapper::toTransactionSubtypeResponse).toList();
    }
    @Transactional(readOnly = true) public TransactionSubtypeResponse obtenerSubtipoTransaccion(String codigo) { return mapper.toTransactionSubtypeResponse(findSubtipoTransaccion(codigo)); }
    @Transactional public TransactionSubtypeResponse crearSubtipoTransaccion(TransactionSubtypeRequest request, String actorUuid) { if (subtipoTransaccionRepository.existsByCodigo(request.code())) throw new BusinessException("ADMIN_TRANSACTION_SUBTYPE_DUPLICATED", "Ya existe el subtipo de transacción", HttpStatus.CONFLICT); SubtipoTransaccion s = subtipoTransaccionRepository.save(SubtipoTransaccion.crear(request.code(), request.name(), enumValue(TipoMovimientoBaseEnum.class, request.baseMovementType(), "ADMIN_MOVEMENT_TYPE_INVALID"), request.description())); auditoriaService.registrar(actorUuid, "CREATE_TRANSACTION_SUBTYPE", "SUBTIPO_TRANSACCION", s.getCodigo(), ResultadoAuditoriaAdminEnum.OK, null); return mapper.toTransactionSubtypeResponse(s); }
    @Transactional public TransactionSubtypeResponse actualizarSubtipoTransaccion(String codigo, TransactionSubtypeRequest request, String actorUuid) { SubtipoTransaccion s = findSubtipoTransaccion(codigo); s.actualizar(request.name(), enumValue(TipoMovimientoBaseEnum.class, request.baseMovementType(), "ADMIN_MOVEMENT_TYPE_INVALID"), request.description(), request.status() == null ? s.getEstado() : enumValue(EstadoRegistroEnum.class, request.status(), "ADMIN_TRANSACTION_SUBTYPE_STATUS_INVALID")); auditoriaService.registrar(actorUuid, "UPDATE_TRANSACTION_SUBTYPE", "SUBTIPO_TRANSACCION", codigo, ResultadoAuditoriaAdminEnum.OK, null); return mapper.toTransactionSubtypeResponse(subtipoTransaccionRepository.save(s)); }

    @Transactional
    public UserCoreResponse crearUsuarioCore(UserCoreRequest request, String actorUuid) {
        if (request.identityUuid() == null || request.identityUuid().isBlank()) {
            throw new BusinessException(
                    "ADMIN_CORE_USER_IDENTITY_REQUIRED",
                    "El UUID de identidad es obligatorio",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (usuarioCoreRepository.existsByUuidIdentidad(request.identityUuid())) {
            throw new BusinessException(
                    "ADMIN_CORE_USER_DUPLICATED",
                    "Ya existe un perfil operativo para esa identidad",
                    HttpStatus.CONFLICT
            );
        }

        if (request.branchCode() != null && !request.branchCode().isBlank()) {
            findSucursal(request.branchCode());
        }

        UsuarioCore usuario = usuarioCoreRepository.save(
                UsuarioCore.crear(
                        request.identityUuid(),
                        request.branchCode(),
                        request.fullName(),
                        request.position()
                )
        );

        auditoriaService.registrar(
                actorUuid,
                "CREATE_CORE_USER",
                "USUARIO_CORE",
                usuario.getUuidUsuarioCore(),
                ResultadoAuditoriaAdminEnum.OK,
                null
        );

        return mapper.toUserCoreResponse(usuario);
    }
    @Transactional(readOnly = true) public UserCoreResponse obtenerUsuarioCore(String uuid) { return mapper.toUserCoreResponse(usuarioCoreRepository.findByUuidUsuarioCore(uuid).orElseThrow(() -> notFound("ADMIN_CORE_USER_NOT_FOUND", "Usuario operativo no encontrado"))); }
    @Transactional public UserCoreResponse cambiarEstadoUsuarioCore(String uuid, ChangeStatusRequest request, String actorUuid) { UsuarioCore u = usuarioCoreRepository.findByUuidUsuarioCore(uuid).orElseThrow(() -> notFound("ADMIN_CORE_USER_NOT_FOUND", "Usuario operativo no encontrado")); u.cambiarEstado(enumValue(EstadoUsuarioCoreEnum.class, request.status(), "ADMIN_CORE_USER_STATUS_INVALID")); auditoriaService.registrar(actorUuid, "CHANGE_CORE_USER_STATUS", "USUARIO_CORE", uuid, ResultadoAuditoriaAdminEnum.OK, null); return mapper.toUserCoreResponse(usuarioCoreRepository.save(u)); }

    private Sucursal findSucursal(String codigo) { return sucursalRepository.findByCodigoSucursal(codigo).orElseThrow(() -> notFound("ADMIN_BRANCH_NOT_FOUND", "Sucursal no encontrada")); }
    private ParametroCore findParametro(String codigo) { return parametroRepository.findById(codigo).orElseThrow(() -> notFound("ADMIN_PARAMETER_NOT_FOUND", "Parámetro no encontrado")); }
    private VentanaOperativa findVentana(String codigo) { return ventanaRepository.findByCodigo(codigo).orElseThrow(() -> notFound("ADMIN_WINDOW_NOT_FOUND", "Ventana operativa no encontrada")); }
    private InstitucionFinanciera findInstitucion(String routingCode) { return institucionRepository.findByRoutingCode(routingCode).orElseThrow(() -> notFound("ADMIN_INSTITUTION_NOT_FOUND", "Institución financiera no encontrada")); }
    private SubtipoCuenta findSubtipoCuenta(String codigo) { return subtipoCuentaRepository.findByCodigo(codigo).orElseThrow(() -> notFound("ADMIN_ACCOUNT_SUBTYPE_NOT_FOUND", "Subtipo de cuenta no encontrado")); }
    private SubtipoTransaccion findSubtipoTransaccion(String codigo) { return subtipoTransaccionRepository.findByCodigo(codigo).orElseThrow(() -> notFound("ADMIN_TRANSACTION_SUBTYPE_NOT_FOUND", "Subtipo de transacción no encontrado")); }

    private BusinessException notFound(String code, String message) { return new BusinessException(code, message, HttpStatus.NOT_FOUND); }
    private static String defaultString(String value, String defaultValue) { return value == null || value.isBlank() ? defaultValue : value; }
    private static <E extends Enum<E>> E enumValue(Class<E> enumClass, String value, String code) { try { return Enum.valueOf(enumClass, value.toUpperCase()); } catch (Exception e) { throw new BusinessException(code, "Valor no permitido: " + value, HttpStatus.BAD_REQUEST); } }
}
