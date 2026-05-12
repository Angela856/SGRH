package cl.duocuc.dsy1103.hotel.repository;

import cl.duocuc.dsy1103.hotel.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByDisponibleTrue();
}