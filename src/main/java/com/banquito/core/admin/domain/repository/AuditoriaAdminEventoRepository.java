package com.banquito.core.admin.domain.repository;

import com.banquito.core.admin.domain.model.AuditoriaAdminEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuditoriaAdminEventoRepository extends JpaRepository<AuditoriaAdminEvento, Long> {

}
