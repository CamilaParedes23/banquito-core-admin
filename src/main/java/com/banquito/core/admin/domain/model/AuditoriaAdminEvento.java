package com.banquito.core.admin.domain.model;

import com.banquito.core.admin.domain.enums.ResultadoAuditoriaAdminEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "AUDITORIA_ADMIN_EVENTO")
public class AuditoriaAdminEvento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "UUID_CORRELACION", length = 36)
    private String uuidCorrelacion;

    @Column(name = "UUID_USUARIO", length = 36)
    private String uuidUsuario;

    @Column(name = "MODULO", length = 60, nullable = false)
    private String modulo;

    @Column(name = "ACCION", length = 80, nullable = false)
    private String accion;

    @Column(name = "ENTIDAD", length = 80, nullable = false)
    private String entidad;

    @Column(name = "ENTIDAD_ID", length = 80)
    private String entidadId;

    @Enumerated(EnumType.STRING)
    @Column(name = "RESULTADO", length = 15, nullable = false)
    private ResultadoAuditoriaAdminEnum resultado;

    @Column(name = "DETALLE_JSON", columnDefinition = "json")
    private String detalleJson;

    @Column(name = "FECHA_EVENTO", nullable = false)
    private LocalDateTime fechaEvento;

    public AuditoriaAdminEvento() {}
    public AuditoriaAdminEvento(Long id) { this.id = id; }

    public static AuditoriaAdminEvento crear(String uuidCorrelacion, String uuidUsuario, String accion, String entidad, String entidadId,
                                             ResultadoAuditoriaAdminEnum resultado, String detalleJson) {
        AuditoriaAdminEvento evento = new AuditoriaAdminEvento();
        evento.uuidCorrelacion = uuidCorrelacion;
        evento.uuidUsuario = uuidUsuario;
        evento.modulo = "ADMIN";
        evento.accion = accion;
        evento.entidad = entidad;
        evento.entidadId = entidadId;
        evento.resultado = resultado;
        evento.detalleJson = detalleJson;
        evento.fechaEvento = LocalDateTime.now();
        return evento;
    }
    @PrePersist public void prePersist() { if (fechaEvento == null) fechaEvento = LocalDateTime.now(); if (resultado == null) resultado = ResultadoAuditoriaAdminEnum.OK; }
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof AuditoriaAdminEvento that)) return false; if (id == null || that.id == null) return false; return Objects.equals(id, that.id); }
    @Override public int hashCode() { return Objects.hashCode(id); }
    @Override public String toString() { return "AuditoriaAdminEvento{" + "id=" + id + ", accion='" + accion + '\'' + ", entidad='" + entidad + '\'' + ", resultado=" + resultado + '}'; }
}
