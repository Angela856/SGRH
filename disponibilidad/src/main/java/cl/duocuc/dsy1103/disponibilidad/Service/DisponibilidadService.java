package cl.duocuc.dsy1103.disponibilidad.Service;
import cl.duocuc.dsy1103.disponibilidad.Client.HabitacionClient;
import cl.duocuc.dsy1103.disponibilidad.DTO.DisponibilidadDTO;
import cl.duocuc.dsy1103.disponibilidad.Model.Disponibilidad;
import cl.duocuc.dsy1103.disponibilidad.Repository.DisponibilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service


public class DisponibilidadService {

@Autowired
    private DisponibilidadRepository repository;

    @Autowired
    private HabitacionClient habitacionClient; 

    public DisponibilidadDTO guardar(DisponibilidadDTO dto) {
        try {
            
            habitacionClient.verificarHabitacionExiste(dto.getHabitacionId());
            
            Disponibilidad disp = new Disponibilidad();
            disp.setHabitacionId(dto.getHabitacionId());
            disp.setDisponible(dto.getDisponible()); 
            
            Disponibilidad guardada = repository.save(disp);
            
            
            return new DisponibilidadDTO(guardada.getId(), guardada.getHabitacionId(), guardada.getDisponible());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error de Integración: No se pudo registrar la disponibilidad. La habitación con ID " + dto.getHabitacionId() + " no existe.");
        }
    }

    public List<DisponibilidadDTO> listar() {
        return repository.findAll().stream()
                
                .map(d -> new DisponibilidadDTO(d.getId(), d.getHabitacionId(), d.getDisponible()))
                .collect(Collectors.toList());
    }

    public DisponibilidadDTO obtenerPorHabitacion(Long habitacionId) {
        Disponibilidad d = repository.findByHabitacionId(habitacionId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró registro de disponibilidad para la habitación: " + habitacionId));
        
        return new DisponibilidadDTO(d.getId(), d.getHabitacionId(), d.getDisponible());
    }
}