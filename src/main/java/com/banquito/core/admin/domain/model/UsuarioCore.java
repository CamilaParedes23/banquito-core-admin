package com.banquito.core.admin.domain.model;

import com.banquito.core.admin.domain.enums.EstadoUsuarioCoreEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "USUARIO_CORE")
public class UsuarioCore {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "UUID_USUARIO_CORE", length = 36, nullable = false)
    private String uuidUsuarioCore;

    @Column(name = "UUID_IDENTIDAD", length = 36, nullable = false)
    private String uuidIdentidad;

    @Column(name = "CODIGO_SUCURSAL", length = 10)
    private String codigoSucursal;

    @Column(name = "NOMBRE_COMPLETO", length = 160, nullable = false)
    private String nombreCompleto;

    @Column(name = "CARGO", length = 100)
    private String cargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_OPERATIVO", length = 15, nullable = false)
    private EstadoUsuarioCoreEnum estadoOperativo;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Version @Column(name = "VERSION", nullable = false)
    private Integer version;

    public UsuarioCore() {}
    public UsuarioCore(Long id) { this.id = id; }

    public static UsuarioCore crear(String uuidIdentidad, String codigoSucursal, String nombreCompleto, String cargo) {
        UsuarioCore usuario = new UsuarioCore();
        usuario.uuidUsuarioCore = UUID.randomUUID().toString();
        usuario.uuidIdentidad = uuidIdentidad;
        usuario.codigoSucursal = codigoSucursal;
        usuario.nombreCompleto = nombreCompleto;
        usuario.cargo = cargo;
        usuario.estadoOperativo = EstadoUsuarioCoreEnum.ACTIVO;
        return usuario;
    }
    public void cambiarEstado(EstadoUsuarioCoreEnum estado) { this.estadoOperativo = estado; }
    public void actualizar(String codigoSucursal, String nombreCompleto, String cargo) { this.codigoSucursal = codigoSucursal; this.nombreCompleto = nombreCompleto; this.cargo = cargo; }
    @PrePersist public void prePersist() { LocalDateTime now = LocalDateTime.now(); if (uuidUsuarioCore == null) uuidUsuarioCore = UUID.randomUUID().toString(); if (estadoOperativo == null) estadoOperativo = EstadoUsuarioCoreEnum.ACTIVO; if (fechaCreacion == null) fechaCreacion = now; if (fechaActualizacion == null) fechaActualizacion = now; }
    @PreUpdate public void preUpdate() { fechaActualizacion = LocalDateTime.now(); }
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof UsuarioCore that)) return false; if (id == null || that.id == null) return false; return Objects.equals(id, that.id); }
    @Override public int hashCode() { return Objects.hashCode(id); }
    @Override public String toString() { return "UsuarioCore{" + "id=" + id + ", uuidUsuarioCore='" + uuidUsuarioCore + '\'' + ", codigoSucursal='" + codigoSucursal + '\'' + ", estadoOperativo=" + estadoOperativo + '}'; }
}
