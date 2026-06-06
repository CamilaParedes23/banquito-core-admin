package com.banquito.core.admin.domain.repository;

    import com.banquito.core.admin.domain.model.SubtipoCuenta;
    import com.banquito.core.admin.domain.enums.EstadoRegistroEnum;
import com.banquito.core.admin.domain.enums.TipoBaseCuentaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface SubtipoCuentaRepository extends JpaRepository<SubtipoCuenta, Integer> {

    Optional<SubtipoCuenta> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<SubtipoCuenta> findByEstadoOrderByNombreAsc(EstadoRegistroEnum estado);
    List<SubtipoCuenta> findByTipoBaseAndEstadoOrderByNombreAsc(TipoBaseCuentaEnum tipoBase, EstadoRegistroEnum estado);

    }
