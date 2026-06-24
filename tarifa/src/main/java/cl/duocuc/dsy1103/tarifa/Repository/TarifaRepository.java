package cl.duocuc.dsy1103.tarifa.Repository;
import cl.duocuc.dsy1103.tarifa.Model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
   
    Optional<Tarifa> findByHabitacionId(Long habitacionId);
}
