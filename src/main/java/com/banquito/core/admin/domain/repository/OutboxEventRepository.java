package com.banquito.core.admin.domain.repository;

    import com.banquito.core.admin.domain.model.OutboxEvent;
    import com.banquito.core.admin.domain.enums.EstadoOutboxEventEnum;
import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findByEstadoOrderByFechaCreacionAsc(EstadoOutboxEventEnum estado);
    long countByEstado(EstadoOutboxEventEnum estado);

    }
