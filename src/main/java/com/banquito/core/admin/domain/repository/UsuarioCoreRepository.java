package com.banquito.core.admin.domain.repository;

    import com.banquito.core.admin.domain.model.UsuarioCore;
    import com.banquito.core.admin.domain.enums.EstadoUsuarioCoreEnum;
import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface UsuarioCoreRepository extends JpaRepository<UsuarioCore, Long> {

    Optional<UsuarioCore> findByUuidUsuarioCore(String uuidUsuarioCore);
    Optional<UsuarioCore> findByUuidIdentidad(String uuidIdentidad);
    boolean existsByUuidIdentidad(String uuidIdentidad);
    List<UsuarioCore> findByCodigoSucursalAndEstadoOperativoOrderByNombreCompletoAsc(String codigoSucursal, EstadoUsuarioCoreEnum estadoOperativo);

    }
