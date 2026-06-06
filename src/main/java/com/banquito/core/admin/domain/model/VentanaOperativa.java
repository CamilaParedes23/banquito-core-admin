package com.banquito.core.admin.domain.model;

import com.banquito.core.admin.domain.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "VENTANA_OPERATIVA")
public class VentanaOperativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "CODIGO", length = 50, nullable = false)
    private String codigo;

    @Column(name = "NOMBRE", length = 120, nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOMINIO_OPERATIVO", length = 30, nullable = false)
    private DominioOperativoEnum dominioOperativo;

    @Column(name = "HORA_INICIO", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "HORA_CORTE", nullable = false)
    private LocalTime horaCorte;

    @Column(name = "HORA_FIN", nullable = false)
    private LocalTime horaFin;

    @Column(name = "DIAS_APLICA", length = 50, nullable = false)
    private String diasAplica;

    @Column(name = "TIMEZONE", length = 60, nullable = false)
    private String timezone;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCION_DESPUES_CORTE", length = 30, nullable = false)
    private AccionDespuesCorteEnum accionDespuesCorte;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 15, nullable = false)
    private EstadoVentanaOperativaEnum estado;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    public VentanaOperativa() {}
    public VentanaOperativa(Integer id) { this.id = id; }

    public static VentanaOperativa crear(String codigo, String nombre, DominioOperativoEnum dominio, LocalTime inicio,
                                         LocalTime corte, LocalTime fin, String diasAplica, String timezone,
                                         AccionDespuesCorteEnum accion) {
        VentanaOperativa ventana = new VentanaOperativa();
        ventana.codigo = codigo;
        ventana.nombre = nombre;
        ventana.dominioOperativo = dominio;
        ventana.horaInicio = inicio;
        ventana.horaCorte = corte;
        ventana.horaFin = fin;
        ventana.diasAplica = diasAplica;
        ventana.timezone = timezone;
        ventana.accionDespuesCorte = accion;
        ventana.estado = EstadoVentanaOperativaEnum.ACTIVA;
        return ventana;
    }

    public void actualizar(String nombre, DominioOperativoEnum dominio, LocalTime inicio, LocalTime corte, LocalTime fin,
                           String diasAplica, String timezone, AccionDespuesCorteEnum accion, EstadoVentanaOperativaEnum estado) {
        this.nombre = nombre;
        this.dominioOperativo = dominio;
        this.horaInicio = inicio;
        this.horaCorte = corte;
        this.horaFin = fin;
        this.diasAplica = diasAplica;
        this.timezone = timezone;
        this.accionDespuesCorte = accion;
        this.estado = estado;
    }

    @PrePersist public void prePersist() { LocalDateTime now = LocalDateTime.now(); if (estado == null) estado = EstadoVentanaOperativaEnum.ACTIVA; if (timezone == null) timezone = "America/Guayaquil"; if (diasAplica == null) diasAplica = "LUN,MAR,MIE,JUE,VIE"; if (fechaCreacion == null) fechaCreacion = now; if (fechaActualizacion == null) fechaActualizacion = now; }
    @PreUpdate public void preUpdate() { fechaActualizacion = LocalDateTime.now(); }
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof VentanaOperativa that)) return false; if (id == null || that.id == null) return false; return Objects.equals(id, that.id); }
    @Override public int hashCode() { return Objects.hashCode(id); }
    @Override public String toString() { return "VentanaOperativa{" + "id=" + id + ", codigo='" + codigo + '\'' + ", dominioOperativo=" + dominioOperativo + ", estado=" + estado + '}'; }
}
