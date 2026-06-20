package com.banquito.core.admin.domain.repository;

    import com.banquito.core.admin.domain.model.Sucursal;
    import com.banquito.core.admin.domain.enums.EstadoSucursalEnum;
import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    Optional<Sucursal> findByCodigoSucursal(String codigoSucursal);
    boolean existsByCodigoSucursal(String codigoSucursal);
    List<Sucursal> findByEstadoOrderByNombreAsc(EstadoSucursalEnum estado);
    long countByEstado(EstadoSucursalEnum estado);

    }
