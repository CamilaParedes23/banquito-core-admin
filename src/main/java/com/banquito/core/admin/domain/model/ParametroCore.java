package com.banquito.core.admin.domain.model;

import com.banquito.core.admin.domain.enums.EstadoRegistroEnum;
import com.banquito.core.admin.domain.enums.TipoDatoParametroEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "PARAMETRO_CORE")
public class ParametroCore {
    @Id
    @Column(name = "CODIGO", length = 60, nullable = false)
    private String codigo;

    @Column(name = "NOMBRE", length = 120, nullable = false)
    private String nombre;

    @Column(name = "VALOR_TEXTO", length = 300, nullable = false)
    private String valorTexto;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DATO", length = 20, nullable = false)
    private TipoDatoParametroEnum tipoDato;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

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

    public ParametroCore() {}
    public ParametroCore(String codigo) { this.codigo = codigo; }

    public static ParametroCore crear(String codigo, String nombre, String valor, TipoDatoParametroEnum tipo, String descripcion) {
        ParametroCore parametro = new ParametroCore();
        parametro.codigo = codigo;
        parametro.nombre = nombre;
        parametro.valorTexto = valor;
        parametro.tipoDato = tipo;
        parametro.descripcion = descripcion;
        parametro.estado = EstadoRegistroEnum.ACTIVO;
        return parametro;
    }
    public void actualizar(String nombre, String valor, TipoDatoParametroEnum tipo, String descripcion, EstadoRegistroEnum estado) { this.nombre = nombre; this.valorTexto = valor; this.tipoDato = tipo; this.descripcion = descripcion; this.estado = estado; }
    @PrePersist public void prePersist() { LocalDateTime now = LocalDateTime.now(); if (tipoDato == null) tipoDato = TipoDatoParametroEnum.STRING; if (estado == null) estado = EstadoRegistroEnum.ACTIVO; if (fechaCreacion == null) fechaCreacion = now; if (fechaActualizacion == null) fechaActualizacion = now; }
    @PreUpdate public void preUpdate() { fechaActualizacion = LocalDateTime.now(); }
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof ParametroCore that)) return false; return Objects.equals(codigo, that.codigo); }
    @Override public int hashCode() { return Objects.hashCode(codigo); }
    @Override public String toString() { return "ParametroCore{" + "codigo='" + codigo + '\'' + ", tipoDato=" + tipoDato + ", estado=" + estado + '}'; }
}
