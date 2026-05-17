package cl.duocuc.dsy1103.disponibilidad.Service;
import cl.duocuc.dsy1103.disponibilidad.DTO.DisponibilidadDTO;
import cl.duocuc.dsy1103.disponibilidad.Model.Disponibilidad;
import cl.duocuc.dsy1103.disponibilidad.Repository.DisponibilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service


public class DisponibilidadService {

@Autowired
    private DisponibilidadRepository repository;

    
    public DisponibilidadDTO guardar(DisponibilidadDTO dto) {
        RestTemplate restTemplate = new RestTemplate();
      
        String url = "http://localhost:3306/habitaciones/" + dto.getHabitacionId();
        
        try {
            // Intenta obtener la habitación. Si no existe, lanzará una excepción e irá al catch.
            restTemplate.getForObject(url, Object.class);
            
            // Convertimos DTO a Entidad para persistir en la BD
            Disponibilidad disp = new Disponibilidad();
            disp.setHabitacionId(dto.getHabitacionId());
            disp.setDisponible(dto.isDisponible());
            
            Disponibilidad guardada = repository.save(disp);
            
            
            return new DisponibilidadDTO(guardada.getId(), guardada.getHabitacionId(), guardada.isDisponible());
        } catch (Exception e) {
            
            return null;
        }
    }

   
    public List<DisponibilidadDTO> listar() {
        return repository.findAll().stream()
                .map(d -> new DisponibilidadDTO(d.getId(), d.getHabitacionId(), d.isDisponible()))
                .collect(Collectors.toList());
    }
}



