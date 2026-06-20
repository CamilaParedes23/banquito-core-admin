package com.banquito.core.admin.domain.repository;

import com.banquito.core.admin.domain.enums.ResultadoAuditoriaAdminEnum;
import com.banquito.core.admin.domain.model.AuditoriaAdminEvento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuditoriaAdminEventoRepository extends JpaRepository<AuditoriaAdminEvento, Long> {

    Optional<AuditoriaAdminEvento> findById(Long id);

    List<AuditoriaAdminEvento> findTop5ByOrderByFechaEventoDesc();

    @Query("""
            SELECT a
            FROM AuditoriaAdminEvento a
            WHERE (:fechaDesde IS NULL OR a.fechaEvento >= :fechaDesde)
              AND (:fechaHasta IS NULL OR a.fechaEvento <= :fechaHasta)
              AND (:modulo IS NULL OR UPPER(a.modulo) = UPPER(:modulo))
              AND (:accion IS NULL OR UPPER(a.accion) LIKE UPPER(CONCAT('%', :accion, '%')))
              AND (:entidad IS NULL OR UPPER(a.entidad) = UPPER(:entidad))
              AND (:entidadId IS NULL OR UPPER(a.entidadId) LIKE UPPER(CONCAT('%', :entidadId, '%')))
              AND (:resultado IS NULL OR a.resultado = :resultado)
            ORDER BY a.fechaEvento DESC
            """)
    Page<AuditoriaAdminEvento> searchAuditEvents(
            @Param("fechaDesde") LocalDateTime fechaDesde,
            @Param("fechaHasta") LocalDateTime fechaHasta,
            @Param("modulo") String modulo,
            @Param("accion") String accion,
            @Param("entidad") String entidad,
            @Param("entidadId") String entidadId,
            @Param("resultado") ResultadoAuditoriaAdminEnum resultado,
            Pageable pageable
    );
}
