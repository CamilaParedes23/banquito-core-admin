package com.banquito.core.admin.api.controller;

import com.banquito.core.admin.api.dto.api.*;
import com.banquito.core.admin.application.service.AdminService;
import com.banquito.core.admin.application.service.AuditoriaAdminService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final AuditoriaAdminService auditoriaService;

    public AdminController(AdminService adminService, AuditoriaAdminService auditoriaService) {
        this.adminService = adminService;
        this.auditoriaService = auditoriaService;
    }

    @GetMapping("/branches")
    public List<BranchResponse> listBranches(@RequestParam(required = false) String status) {
        return adminService.listarSucursales(status);
    }

    @GetMapping("/branches/{branchCode}")
    public BranchResponse getBranch(@PathVariable String branchCode) {
        return adminService.obtenerSucursal(branchCode);
    }

    @PostMapping("/branches")
    public ResponseEntity<BranchResponse> createBranch(@Valid @RequestBody BranchRequest request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.crearSucursal(request, subject(authentication)));
    }

    @PatchMapping("/branches/{branchCode}")
    public BranchResponse updateBranch(@PathVariable String branchCode, @Valid @RequestBody BranchRequest request, Authentication authentication) {
        return adminService.actualizarSucursal(branchCode, request, subject(authentication));
    }

    @PatchMapping("/branches/{branchCode}/status")
    public BranchResponse changeBranchStatus(@PathVariable String branchCode, @Valid @RequestBody ChangeStatusRequest request, Authentication authentication) {
        return adminService.cambiarEstadoSucursal(branchCode, request, subject(authentication));
    }

    @GetMapping("/holidays")
    public List<HolidayResponse> listHolidays(@RequestParam(required = false) String status) {
        return adminService.listarFeriados(status);
    }

    @PostMapping("/holidays")
    public ResponseEntity<HolidayResponse> createHoliday(@Valid @RequestBody HolidayRequest request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.crearFeriado(request, subject(authentication)));
    }

    @PatchMapping("/holidays/{holidayDate}/status")
    public HolidayResponse changeHolidayStatus(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate holidayDate,
                                               @Valid @RequestBody ChangeStatusRequest request,
                                               Authentication authentication) {
        return adminService.cambiarEstadoFeriado(holidayDate, request, subject(authentication));
    }

    @GetMapping("/business-calendar/{date}")
    public BusinessDayResponse getBusinessDay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return adminService.obtenerDiaHabil(date);
    }

    @GetMapping("/business-calendar/{date}/next-business-day")
    public BusinessDayResponse getNextBusinessDay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return adminService.obtenerSiguienteDiaHabil(date);
    }

    @GetMapping("/parameters")
    public List<ParameterResponse> listParameters(@RequestParam(required = false) String status) {
        return adminService.listarParametros(status);
    }

    @GetMapping("/parameters/{code}")
    public ParameterResponse getParameter(@PathVariable String code) {
        return adminService.obtenerParametro(code);
    }

    @PostMapping("/parameters")
    public ResponseEntity<ParameterResponse> createParameter(@Valid @RequestBody ParameterRequest request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.crearParametro(request, subject(authentication)));
    }

    @PatchMapping("/parameters/{code}")
    public ParameterResponse updateParameter(@PathVariable String code, @Valid @RequestBody ParameterRequest request, Authentication authentication) {
        return adminService.actualizarParametro(code, request, subject(authentication));
    }

    @GetMapping("/operational-windows")
    public List<OperationalWindowResponse> listOperationalWindows(@RequestParam(required = false) String domain,
                                                                  @RequestParam(required = false) String status) {
        return adminService.listarVentanas(domain, status);
    }

    @GetMapping("/operational-windows/{code}")
    public OperationalWindowResponse getOperationalWindow(@PathVariable String code) {
        return adminService.obtenerVentana(code);
    }

    @PostMapping("/operational-windows")
    public ResponseEntity<OperationalWindowResponse> createOperationalWindow(@Valid @RequestBody OperationalWindowRequest request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.crearVentana(request, subject(authentication)));
    }

    @PatchMapping("/operational-windows/{code}")
    public OperationalWindowResponse updateOperationalWindow(@PathVariable String code, @Valid @RequestBody OperationalWindowRequest request, Authentication authentication) {
        return adminService.actualizarVentana(code, request, subject(authentication));
    }

    @GetMapping("/financial-institutions")
    public List<FinancialInstitutionResponse> listFinancialInstitutions(@RequestParam(required = false) String status) {
        return adminService.listarInstituciones(status);
    }

    @GetMapping("/financial-institutions/{routingCode}")
    public FinancialInstitutionResponse getFinancialInstitution(@PathVariable String routingCode) {
        return adminService.obtenerInstitucion(routingCode);
    }

    @PostMapping("/financial-institutions")
    public ResponseEntity<FinancialInstitutionResponse> createFinancialInstitution(@Valid @RequestBody FinancialInstitutionRequest request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.crearInstitucion(request, subject(authentication)));
    }

    @PatchMapping("/financial-institutions/{routingCode}")
    public FinancialInstitutionResponse updateFinancialInstitution(@PathVariable String routingCode, @Valid @RequestBody FinancialInstitutionRequest request, Authentication authentication) {
        return adminService.actualizarInstitucion(routingCode, request, subject(authentication));
    }

    @GetMapping("/account-subtypes")
    public List<AccountSubtypeResponse> listAccountSubtypes(@RequestParam(required = false) String baseType,
                                                            @RequestParam(required = false) String status) {
        return adminService.listarSubtiposCuenta(baseType, status);
    }

    @GetMapping("/account-subtypes/{code}")
    public AccountSubtypeResponse getAccountSubtype(@PathVariable String code) {
        return adminService.obtenerSubtipoCuenta(code);
    }

    @PostMapping("/account-subtypes")
    public ResponseEntity<AccountSubtypeResponse> createAccountSubtype(@Valid @RequestBody AccountSubtypeRequest request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.crearSubtipoCuenta(request, subject(authentication)));
    }

    @PatchMapping("/account-subtypes/{code}")
    public AccountSubtypeResponse updateAccountSubtype(@PathVariable String code, @Valid @RequestBody AccountSubtypeRequest request, Authentication authentication) {
        return adminService.actualizarSubtipoCuenta(code, request, subject(authentication));
    }

    @GetMapping("/transaction-subtypes")
    public List<TransactionSubtypeResponse> listTransactionSubtypes(@RequestParam(required = false) String baseMovementType,
                                                                    @RequestParam(required = false) String status) {
        return adminService.listarSubtiposTransaccion(baseMovementType, status);
    }

    @GetMapping("/transaction-subtypes/{code}")
    public TransactionSubtypeResponse getTransactionSubtype(@PathVariable String code) {
        return adminService.obtenerSubtipoTransaccion(code);
    }

    @PostMapping("/transaction-subtypes")
    public ResponseEntity<TransactionSubtypeResponse> createTransactionSubtype(@Valid @RequestBody TransactionSubtypeRequest request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.crearSubtipoTransaccion(request, subject(authentication)));
    }

    @PatchMapping("/transaction-subtypes/{code}")
    public TransactionSubtypeResponse updateTransactionSubtype(@PathVariable String code, @Valid @RequestBody TransactionSubtypeRequest request, Authentication authentication) {
        return adminService.actualizarSubtipoTransaccion(code, request, subject(authentication));
    }

    @PostMapping("/users")
    public ResponseEntity<UserCoreResponse> createCoreUser(@Valid @RequestBody UserCoreRequest request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.crearUsuarioCore(request, subject(authentication)));
    }

    @GetMapping("/users/{userCoreUuid}")
    public UserCoreResponse getCoreUser(@PathVariable String userCoreUuid) {
        return adminService.obtenerUsuarioCore(userCoreUuid);
    }

    @PatchMapping("/users/{userCoreUuid}/status")
    public UserCoreResponse changeCoreUserStatus(@PathVariable String userCoreUuid,
                                                 @Valid @RequestBody ChangeStatusRequest request,
                                                 Authentication authentication) {
        return adminService.cambiarEstadoUsuarioCore(userCoreUuid, request, subject(authentication));
    }

    @GetMapping("/audit/events")
    public List<AuditoriaEventoResponse> listAuditEvents(@RequestParam(required = false) String fechaDesde,
                                                          @RequestParam(required = false) String fechaHasta,
                                                          @RequestParam(required = false) String entidad,
                                                          @RequestParam(required = false) String resultado) {
        return auditoriaService.listarTodos();
    }

    @GetMapping("/audit/recent")
    public List<AuditoriaEventoResponse> listRecentAuditEvents() {
        return auditoriaService.listarRecientes();
    }

    @GetMapping("/metrics")
    public MetricsResponse getMetrics() {
        return new MetricsResponse(
            540,
            1501,
            12468395.35,
            auditoriaService.contarTotal(),
            14,
            2,
            3
        );
    }

    private String subject(Authentication authentication) {
        return authentication == null ? null : String.valueOf(authentication.getPrincipal());
    }
}
