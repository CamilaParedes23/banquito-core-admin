package com.banquito.core.admin.domain.model;

import com.banquito.core.admin.domain.enums.EstadoRegistroEnum;
import com.banquito.core.admin.domain.enums.TipoBaseCuentaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "SUBTIPO_CUENTA")
public class SubtipoCuenta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "CODIGO", length = 30, nullable = false)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_BASE", length = 15, nullable = false)
    private TipoBaseCuentaEnum tipoBase;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 300)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 15, nullable = false)
    private EstadoRegistroEnum estado;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Version @Column(name = "VERSION", nullable = false)
    private Integer version;

    public SubtipoCuenta() {}
    public SubtipoCuenta(Integer id) { this.id = id; }
    public static SubtipoCuenta crear(String codigo, TipoBaseCuentaEnum tipoBase, String nombre, String descripcion) { SubtipoCuenta s = new SubtipoCuenta(); s.codigo = codigo; s.tipoBase = tipoBase; s.nombre = nombre; s.descripcion = descripcion; s.estado = EstadoRegistroEnum.ACTIVO; return s; }
    public void actualizar(TipoBaseCuentaEnum tipoBase, String nombre, String descripcion, EstadoRegistroEnum estado) { this.tipoBase = tipoBase; this.nombre = nombre; this.descripcion = descripcion; this.estado = estado; }
    @PrePersist public void prePersist() { LocalDateTime now = LocalDateTime.now(); if (estado == null) estado = EstadoRegistroEnum.ACTIVO; if (fechaCreacion == null) fechaCreacion = now; if (fechaActualizacion == null) fechaActualizacion = now; }
    @PreUpdate public void preUpdate() { fechaActualizacion = LocalDateTime.now(); }
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof SubtipoCuenta that)) return false; if (id == null || that.id == null) return false; return Objects.equals(id, that.id); }
    @Override public int hashCode() { return Objects.hashCode(id); }
    @Override public String toString() { return "SubtipoCuenta{" + "id=" + id + ", codigo='" + codigo + '\'' + ", tipoBase=" + tipoBase + ", estado=" + estado + '}'; }
}
