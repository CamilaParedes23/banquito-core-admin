package com.banquito.core.admin.domain.repository;

    import com.banquito.core.admin.domain.model.VentanaOperativa;
    import com.banquito.core.admin.domain.enums.DominioOperativoEnum;
import com.banquito.core.admin.domain.enums.EstadoVentanaOperativaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface VentanaOperativaRepository extends JpaRepository<VentanaOperativa, Integer> {

    Optional<VentanaOperativa> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<VentanaOperativa> findByEstadoOrderByCodigoAsc(EstadoVentanaOperativaEnum estado);
    List<VentanaOperativa> findByDominioOperativoAndEstadoOrderByCodigoAsc(DominioOperativoEnum dominioOperativo, EstadoVentanaOperativaEnum estado);

    }
