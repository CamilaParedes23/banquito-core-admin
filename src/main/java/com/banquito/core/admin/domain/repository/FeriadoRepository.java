package com.banquito.core.admin.domain.repository;

    import com.banquito.core.admin.domain.model.Feriado;
    import com.banquito.core.admin.domain.enums.EstadoRegistroEnum;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface FeriadoRepository extends JpaRepository<Feriado, LocalDate> {

    List<Feriado> findByEstadoOrderByFechaFeriadoAsc(EstadoRegistroEnum estado);

    }
