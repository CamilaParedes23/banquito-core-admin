package com.banquito.core.admin.domain.model;

import com.banquito.core.admin.domain.enums.EstadoSucursalEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "SUCURSAL")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "UUID_SUCURSAL", length = 36, nullable = false)
    private String uuidSucursal;

    @Column(name = "CODIGO_SUCURSAL", length = 10, nullable = false)
    private String codigoSucursal;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "CIUDAD", length = 80, nullable = false)
    private String ciudad;

    @Column(name = "DIRECCION", length = 300)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 15, nullable = false)
    private EstadoSucursalEnum estado;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    public Sucursal() {}
    public Sucursal(Integer id) { this.id = id; }

    public static Sucursal crear(String codigo, String nombre, String ciudad, String direccion) {
        Sucursal sucursal = new Sucursal();
        sucursal.uuidSucursal = UUID.randomUUID().toString();
        sucursal.codigoSucursal = codigo;
        sucursal.nombre = nombre;
        sucursal.ciudad = ciudad;
        sucursal.direccion = direccion;
        sucursal.estado = EstadoSucursalEnum.ACTIVA;
        return sucursal;
    }

    public void actualizar(String nombre, String ciudad, String direccion) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    public void cambiarEstado(EstadoSucursalEnum estado) { this.estado = estado; }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (uuidSucursal == null) uuidSucursal = UUID.randomUUID().toString();
        if (estado == null) estado = EstadoSucursalEnum.ACTIVA;
        if (fechaCreacion == null) fechaCreacion = now;
        if (fechaActualizacion == null) fechaActualizacion = now;
    }

    @PreUpdate
    public void preUpdate() { fechaActualizacion = LocalDateTime.now(); }

    @Override
    public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof Sucursal that)) return false; if (id == null || that.id == null) return false; return Objects.equals(id, that.id); }
    @Override
    public int hashCode() { return Objects.hashCode(id); }
    @Override
    public String toString() { return "Sucursal{" + "id=" + id + ", codigoSucursal='" + codigoSucursal + '\'' + ", nombre='" + nombre + '\'' + ", estado=" + estado + '}'; }
}
