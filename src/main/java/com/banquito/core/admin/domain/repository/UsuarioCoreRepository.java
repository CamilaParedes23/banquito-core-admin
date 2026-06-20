package com.banquito.core.admin.domain.repository;

import com.banquito.core.admin.domain.enums.EstadoUsuarioCoreEnum;
import com.banquito.core.admin.domain.model.UsuarioCore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioCoreRepository extends JpaRepository<UsuarioCore, Long> {

    Optional<UsuarioCore> findByUuidUsuarioCore(String uuidUsuarioCore);
    Optional<UsuarioCore> findByUuidIdentidad(String uuidIdentidad);
    boolean existsByUuidIdentidad(String uuidIdentidad);
    List<UsuarioCore> findByCodigoSucursalAndEstadoOperativoOrderByNombreCompletoAsc(String codigoSucursal, EstadoUsuarioCoreEnum estadoOperativo);
    long countByEstadoOperativo(EstadoUsuarioCoreEnum estadoOperativo);

    @Query("""
            SELECT u
            FROM UsuarioCore u
            WHERE (:branchCode IS NULL OR u.codigoSucursal = :branchCode)
              AND (:status IS NULL OR u.estadoOperativo = :status)
              AND (:search IS NULL OR
                   UPPER(u.uuidUsuarioCore) LIKE UPPER(CONCAT('%', :search, '%')) OR
                   UPPER(u.uuidIdentidad) LIKE UPPER(CONCAT('%', :search, '%')) OR
                   UPPER(u.nombreCompleto) LIKE UPPER(CONCAT('%', :search, '%')) OR
                   UPPER(u.cargo) LIKE UPPER(CONCAT('%', :search, '%')))
            ORDER BY u.nombreCompleto ASC
            """)
    Page<UsuarioCore> searchCoreUsers(
            @Param("branchCode") String branchCode,
            @Param("status") EstadoUsuarioCoreEnum status,
            @Param("search") String search,
            Pageable pageable
    );
}
