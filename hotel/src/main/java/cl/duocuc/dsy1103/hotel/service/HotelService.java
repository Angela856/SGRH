package cl.duocuc.dsy1103.hotel.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private HotelRepository repository;

    @Autowired
    private HotelMapper mapper;

    public List<HotelResponse> obtenerTodosLosHoteles() {
        log.info("Obteniendo la lista de todos los hoteles");
        List<Hotel> hoteles = repository.findAll();
        return hoteles.stream().map(mapper::toResponse).toList();
    }

    public HotelResponse obtenerHotelPorId(Long id) {
        log.info("Buscando hotel con id: {}", id);
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hotel no encontrado con el id: " + id));
        return mapper.toResponse(hotel);
    }

    public HotelResponse crearHotel(HotelRequest request) {
        log.info("Creando un nuevo hotel: {}", request.getNombre());
        Hotel hotel = repository.save(mapper.toEntity(request));
        return mapper.toResponse(hotel);
    }

    public HotelResponse actualizarHotel(Long id, HotelRequest request) {
        log.info("Actualizando hotel con id: {}", id);
        Hotel existingHotel = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hotel no encontrado con el id: " + id));

        existingHotel.setNombre(request.getNombre());
        existingHotel.setDireccion(request.getDireccion());
        existingHotel.setEstrellas(request.getEstrellas()); // Error corregido aquí

        Hotel updatedHotel = repository.save(existingHotel);
        return mapper.toResponse(updatedHotel);
    }

    public void eliminarHotel(Long id) {
        log.info("Eliminando hotel con id: {}", id);
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Hotel no encontrado con el id: " + id);
        }
        repository.deleteById(id);
    }
}