package com.banquito.core.admin.domain.repository;

    import com.banquito.core.admin.domain.model.ParametroCore;
    import com.banquito.core.admin.domain.enums.EstadoRegistroEnum;
import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface ParametroCoreRepository extends JpaRepository<ParametroCore, String> {

    List<ParametroCore> findByEstadoOrderByCodigoAsc(EstadoRegistroEnum estado);
    long countByEstado(EstadoRegistroEnum estado);

    }
