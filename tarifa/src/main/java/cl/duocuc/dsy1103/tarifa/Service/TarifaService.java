package cl.duocuc.dsy1103.tarifa.Service;
import cl.duocuc.dsy1103.tarifa.DTO.TarifaDTO;
import cl.duocuc.dsy1103.tarifa.Model.Tarifa;
import cl.duocuc.dsy1103.tarifa.Repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarifaService {

@Autowired
    private TarifaRepository repository;

    public TarifaDTO guardar(TarifaDTO dto) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:3306/habitaciones/" + dto.getHabitacionId();

        try {
            
            restTemplate.getForObject(url, Object.class);

            Tarifa tarifa = new Tarifa();
            tarifa.setHabitacionId(dto.getHabitacionId());
            tarifa.setPrecioNoche(dto.getPrecioNoche());
            tarifa.setTemporada(dto.getTemporada());

            Tarifa guardada = repository.save(tarifa);
            return new TarifaDTO(guardada.getId(), guardada.getHabitacionId(), guardada.getPrecioNoche(), guardada.getTemporada());
        } catch (Exception e) {
            
            throw new IllegalArgumentException("No se puede asignar tarifa. La habitación con ID " + dto.getHabitacionId() + " no existe.");
        }
    }

    public List<TarifaDTO> listar() {
        return repository.findAll().stream()
                .map(t -> new TarifaDTO(t.getId(), t.getHabitacionId(), t.getPrecioNoche(), t.getTemporada()))
                .collect(Collectors.toList());
    }

    public TarifaDTO obtenerPorHabitacion(Long habitacionId) {
        Tarifa tarifa = repository.findByHabitacionId(habitacionId)
                .orElseThrow(() -> new IllegalArgumentException("Tarifa no encontrada para la habitación con ID: " + habitacionId));
        
        return new TarifaDTO(tarifa.getId(), tarifa.getHabitacionId(), tarifa.getPrecioNoche(), tarifa.getTemporada());
    }
}