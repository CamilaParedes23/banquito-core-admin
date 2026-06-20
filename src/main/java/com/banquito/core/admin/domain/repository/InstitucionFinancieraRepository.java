package com.banquito.core.admin.domain.repository;

    import com.banquito.core.admin.domain.model.InstitucionFinanciera;
    import com.banquito.core.admin.domain.enums.EstadoInstitucionFinancieraEnum;
import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface InstitucionFinancieraRepository extends JpaRepository<InstitucionFinanciera, Integer> {

    Optional<InstitucionFinanciera> findByRoutingCode(String routingCode);
    boolean existsByRoutingCode(String routingCode);
    List<InstitucionFinanciera> findByEstadoOrderByNombreAsc(EstadoInstitucionFinancieraEnum estado);
    long countByEstado(EstadoInstitucionFinancieraEnum estado);

    }
