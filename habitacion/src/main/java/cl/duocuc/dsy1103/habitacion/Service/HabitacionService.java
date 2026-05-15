package cl.duocuc.dsy1103.habitacion.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duocuc.dsy1103.habitacion.Model.Habitacion;
import cl.duocuc.dsy1103.habitacion.Repository.HabitacionRepository;

import java.util.List;

@Service
@Slf4j

public class HabitacionService {

@Autowired
    private HabitacionRepository repository;

    public Habitacion guardar(Habitacion habitacion) {
        log.info("Guardando habitación nro: {} para hotel ID: {}", habitacion.getNumero(), habitacion.getHotelId());
        return repository.save(habitacion);
    }

    public List<Habitacion> listarPorHotel(Long hotelId) {
        log.info("Listando habitaciones para el hotel: {}", hotelId);
        return repository.findByHotelId(hotelId);
    }

    public Habitacion buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        log.warn("Eliminando habitación con ID: {}", id);
        repository.deleteById(id);
    }    

}
