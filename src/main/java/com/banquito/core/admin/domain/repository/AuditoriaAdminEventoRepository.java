package com.banquito.core.admin.domain.repository;

import com.banquito.core.admin.domain.model.AuditoriaAdminEvento;
import com.banquito.core.admin.domain.enums.ResultadoAuditoriaAdminEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuditoriaAdminEventoRepository extends JpaRepository<AuditoriaAdminEvento, Long> {

    List<AuditoriaAdminEvento> findAllByOrderByFechaEventoDesc();
    List<AuditoriaAdminEvento> findTop5ByOrderByFechaEventoDesc();
    List<AuditoriaAdminEvento> findByFechaEventoBetweenOrderByFechaEventoDesc(LocalDateTime desde, LocalDateTime hasta);
    List<AuditoriaAdminEvento> findByEntidadAndResultadoOrderByFechaEventoDesc(String entidad, ResultadoAuditoriaAdminEnum resultado);
    long count();
    long countByResultado(ResultadoAuditoriaAdminEnum resultado);

}
