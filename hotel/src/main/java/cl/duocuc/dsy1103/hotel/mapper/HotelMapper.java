package cl.duocuc.dsy1103.hotel.mapper;

import cl.duocuc.dsy1103.hotel.dto.HotelRequest;
import cl.duocuc.dsy1103.hotel.dto.HotelResponse;
import cl.duocuc.dsy1103.hotel.model.Hotel;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class HotelMapper {

    public Hotel toEntity(HotelRequest request) {
        if (request == null) {
            return null;
        }
        Hotel hotel = new Hotel();
        hotel.setNombre(request.getNombre());
        hotel.setDireccion(request.getDireccion());
        hotel.setEstrellas(request.getEstrellas());
        hotel.setNumeroHabitacion(request.getNumeroHabitacion());
        hotel.setPrecioPorNoche(request.getPrecioPorNoche());
        hotel.setDisponible(request.getDisponible());
        
        return hotel;
    }

    public HotelResponse toResponse(Hotel entity) {
        if (entity == null) {
            return null;
        }
        return HotelResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .direccion(entity.getDireccion())
                .estrellas(entity.getEstrellas())
                .numeroHabitacion(entity.getNumeroHabitacion())
                .precioPorNoche(entity.getPrecioPorNoche())
                .disponible(entity.getDisponible())
                .createdAt(LocalDateTime.now()) 
                .build();
    }
}