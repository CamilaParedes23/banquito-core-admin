package com.banquito.core.admin.domain.model;

import com.banquito.core.admin.domain.enums.EstadoRegistroEnum;
import com.banquito.core.admin.domain.enums.TipoMovimientoBaseEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "SUBTIPO_TRANSACCION")
public class SubtipoTransaccion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "CODIGO", length = 40, nullable = false)
    private String codigo;

    @Column(name = "NOMBRE", length = 120, nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_MOVIMIENTO_BASE", length = 10, nullable = false)
    private TipoMovimientoBaseEnum tipoMovimientoBase;

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

    public SubtipoTransaccion() {}
    public SubtipoTransaccion(Integer id) { this.id = id; }
    public static SubtipoTransaccion crear(String codigo, String nombre, TipoMovimientoBaseEnum tipo, String descripcion) { SubtipoTransaccion s = new SubtipoTransaccion(); s.codigo = codigo; s.nombre = nombre; s.tipoMovimientoBase = tipo; s.descripcion = descripcion; s.estado = EstadoRegistroEnum.ACTIVO; return s; }
    public void actualizar(String nombre, TipoMovimientoBaseEnum tipo, String descripcion, EstadoRegistroEnum estado) { this.nombre = nombre; this.tipoMovimientoBase = tipo; this.descripcion = descripcion; this.estado = estado; }
    @PrePersist public void prePersist() { LocalDateTime now = LocalDateTime.now(); if (estado == null) estado = EstadoRegistroEnum.ACTIVO; if (fechaCreacion == null) fechaCreacion = now; if (fechaActualizacion == null) fechaActualizacion = now; }
    @PreUpdate public void preUpdate() { fechaActualizacion = LocalDateTime.now(); }
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof SubtipoTransaccion that)) return false; if (id == null || that.id == null) return false; return Objects.equals(id, that.id); }
    @Override public int hashCode() { return Objects.hashCode(id); }
    @Override public String toString() { return "SubtipoTransaccion{" + "id=" + id + ", codigo='" + codigo + '\'' + ", tipoMovimientoBase=" + tipoMovimientoBase + ", estado=" + estado + '}'; }
}
