package cl.duocuc.dsy1103.hotel.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import cl.duocuc.dsy1103.hotel.dto.HotelRequest;
import cl.duocuc.dsy1103.hotel.dto.HotelResponse;
import cl.duocuc.dsy1103.hotel.mapper.HotelMapper;
import cl.duocuc.dsy1103.hotel.model.Hotel;
import cl.duocuc.dsy1103.hotel.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    HotelService(HotelMapper mapper, HotelRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<HotelResponse> obtenerTodosLosHoteles() {
        log.info("Obteniendo la lista de todas las habitaciones de hotel");
        List<Hotel> hoteles = repository.findAll();
        return hoteles.stream().map(mapper::toResponse).toList();
    }

    public HotelResponse obtenerHotelPorId(Long id) {
        log.info("Buscando habitación/hotel con id: {}", id);
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro no encontrado con el id: " + id));
        return mapper.toResponse(hotel);
    }

    public HotelResponse crearHotel(HotelRequest request) {
        log.info("Registrando una nueva habitación para el hotel: {}", request.getNombre());
        Hotel hotel = repository.save(mapper.toEntity(request));
        return mapper.toResponse(hotel);
    }

    public HotelResponse actualizarHotel(Long id, HotelRequest request) {
        log.info("Actualizando registro de hotel/habitación con id: {}", id);
        Hotel existingHotel = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro no encontrado con el id: " + id));

        // Campos antiguos
        existingHotel.setNombre(request.getNombre());
        existingHotel.setDireccion(request.getDireccion());
        existingHotel.setEstrellas(request.getEstrellas()); 

        // Nuevos campos lógicos actualizados
        existingHotel.setNumeroHabitacion(request.getNumeroHabitacion());
        existingHotel.setPrecioPorNoche(request.getPrecioPorNoche());
        existingHotel.setDisponible(request.getDisponible());

        Hotel updatedHotel = repository.save(existingHotel);
        return mapper.toResponse(updatedHotel);
    }

    public void eliminarHotel(Long id) {
        log.info("Eliminando registro de hotel/habitación con id: {}", id);
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Registro no encontrado con el id: " + id);
        }
        repository.deleteById(id);
    }
}