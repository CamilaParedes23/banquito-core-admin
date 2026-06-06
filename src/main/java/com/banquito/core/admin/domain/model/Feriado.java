package com.banquito.core.admin.domain.model;

import com.banquito.core.admin.domain.enums.EstadoRegistroEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "FERIADO")
public class Feriado {
    @Id
    @Column(name = "FECHA_FERIADO", nullable = false)
    private LocalDate fechaFeriado;

    @Column(name = "NOMBRE", length = 120, nullable = false)
    private String nombre;

    @Column(name = "ES_FIN_SEMANA", nullable = false)
    private Boolean esFinSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 15, nullable = false)
    private EstadoRegistroEnum estado;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    public Feriado() {}
    public Feriado(LocalDate fechaFeriado) { this.fechaFeriado = fechaFeriado; }

    public static Feriado crear(LocalDate fecha, String nombre, Boolean esFinSemana) {
        Feriado feriado = new Feriado();
        feriado.fechaFeriado = fecha;
        feriado.nombre = nombre;
        feriado.esFinSemana = esFinSemana != null && esFinSemana;
        feriado.estado = EstadoRegistroEnum.ACTIVO;
        return feriado;
    }
    public void cambiarEstado(EstadoRegistroEnum estado) { this.estado = estado; }

    @PrePersist
    public void prePersist() { LocalDateTime now = LocalDateTime.now(); if (estado == null) estado = EstadoRegistroEnum.ACTIVO; if (esFinSemana == null) esFinSemana = false; if (fechaCreacion == null) fechaCreacion = now; if (fechaActualizacion == null) fechaActualizacion = now; }
    @PreUpdate
    public void preUpdate() { fechaActualizacion = LocalDateTime.now(); }
    @Override
    public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof Feriado that)) return false; return Objects.equals(fechaFeriado, that.fechaFeriado); }
    @Override
    public int hashCode() { return Objects.hashCode(fechaFeriado); }
    @Override
    public String toString() { return "Feriado{" + "fechaFeriado=" + fechaFeriado + ", nombre='" + nombre + '\'' + ", estado=" + estado + '}'; }
}
