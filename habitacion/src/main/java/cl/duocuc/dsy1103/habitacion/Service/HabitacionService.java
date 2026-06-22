package cl.duocuc.dsy1103.habitacion.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duocuc.dsy1103.habitacion.DTO.HabitacionDTO;
import cl.duocuc.dsy1103.habitacion.Model.Habitacion;
import cl.duocuc.dsy1103.habitacion.Repository.HabitacionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HabitacionService {
@Autowired
    private HabitacionRepository repository;

    // 1. Guardar adaptado a DTO
    public HabitacionDTO guardar(HabitacionDTO dto) {
        log.info("Guardando habitación nro: {} para hotel ID: {}", dto.getNumero(), dto.getHotelId());
        
        // Convertimos DTO a Entidad para almacenar en Laragon
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(dto.getNumero());
        habitacion.setHotelId(dto.getHotelId());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecioBase(dto.getPrecioBase());
        
        Habitacion guardada = repository.save(habitacion);
        
        // Retornamos el DTO de salida estructurado
        return convertToDTO(guardada);
    }

    // 2. Listar por Hotel adaptado a DTO con Streams de Java
    public List<HabitacionDTO> listarPorHotel(Long hotelId) {
        log.info("Listando habitaciones para el hotel: {}", hotelId);
        return repository.findByHotelId(hotelId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 3. Buscar por ID adaptado para integrarse con OpenFeign y lanzar 404
    public HabitacionDTO buscarPorId(Long id) {
        log.info("Buscando habitación por ID: {}", id);
        // CORRECCIÓN CRÍTICA: En lugar de orElse(null), lanzamos la excepción que procesa el ControllerAdvice para arrojar 404
        Habitacion h = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La habitación solicitada con el ID " + id + " no existe en el sistema."));
        return convertToDTO(h);
    }

    // 4. Eliminar
    public void eliminar(Long id) {
        log.warn("Eliminando habitación con ID: {}", id);
        // Validamos primero si existe antes de eliminar de forma abrupta
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. La habitación con ID " + id + " no existe.");
        }
        repository.deleteById(id);
    }    

    // Método helper/auxiliar privado para centralizar la conversión de Entidad a DTO
    private HabitacionDTO convertToDTO(Habitacion h) {
        return new HabitacionDTO(
            h.getId(),
            h.getHotelId(),
            h.getNumero(),
            h.getTipo(),
            h.getPrecioBase()
        );
    }
}