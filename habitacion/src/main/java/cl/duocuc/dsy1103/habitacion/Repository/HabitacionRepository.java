package cl.duocuc.dsy1103.habitacion.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import cl.duocuc.dsy1103.habitacion.Model.Habitacion;

import java.util.List;




public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    // Buscador por hotel, similar a como el hospital busca por especialidad o sala
    List<Habitacion> findByHotelId(Long hotelId); 

    }

