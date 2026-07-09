package cl.duocuc.dsy1103.habitacion.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duocuc.dsy1103.habitacion.Model.Habitacion;
import java.util.List;

@Repository


public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
  // Buscador por hotel derivado automáticamente por Spring Data JPA
    List<Habitacion> findByHotelId(Long hotelId);

    }

