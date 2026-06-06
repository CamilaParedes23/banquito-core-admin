package com.banquito.core.admin.domain.repository;

    import com.banquito.core.admin.domain.model.SubtipoTransaccion;
    import com.banquito.core.admin.domain.enums.EstadoRegistroEnum;
import com.banquito.core.admin.domain.enums.TipoMovimientoBaseEnum;
import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface SubtipoTransaccionRepository extends JpaRepository<SubtipoTransaccion, Integer> {

    Optional<SubtipoTransaccion> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<SubtipoTransaccion> findByEstadoOrderByNombreAsc(EstadoRegistroEnum estado);
    List<SubtipoTransaccion> findByTipoMovimientoBaseAndEstadoOrderByNombreAsc(TipoMovimientoBaseEnum tipoMovimientoBase, EstadoRegistroEnum estado);

    }
