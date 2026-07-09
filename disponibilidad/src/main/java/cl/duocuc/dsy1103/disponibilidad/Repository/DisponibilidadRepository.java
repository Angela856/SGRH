package cl.duocuc.dsy1103.disponibilidad.Repository;
import cl.duocuc.dsy1103.disponibilidad.Model.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {
 Optional<Disponibilidad> findByHabitacionId(Long habitacionId);
}
