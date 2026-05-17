package cl.duocuc.dsy1103.tarifa.Repository;
import cl.duocuc.dsy1103.tarifa.Model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
   
    Optional<Tarifa> findByHabitacionId(Long habitacionId);
}
