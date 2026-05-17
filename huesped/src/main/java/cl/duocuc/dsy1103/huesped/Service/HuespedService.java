package cl.duocuc.dsy1103.huesped.Service;
import cl.duocuc.dsy1103.huesped.DTO.HuespedDTO;
import cl.duocuc.dsy1103.huesped.Model.Huesped;
import cl.duocuc.dsy1103.huesped.Repository.HuespedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HuespedService {

@Autowired
    private HuespedRepository repository;

    
    public HuespedDTO guardar(HuespedDTO dto) {
        Huesped huesped = new Huesped();
        huesped.setNombre(dto.getNombre());
        huesped.setCorreo(dto.getCorreo());
        huesped.setDocumentoIdentidad(dto.getDocumentoIdentidad());

        Huesped guardado = repository.save(huesped);
        return new HuespedDTO(guardado.getId(), guardado.getNombre(), guardado.getCorreo(), guardado.getDocumentoIdentidad());
    }

    
    public List<HuespedDTO> listar() {
        return repository.findAll().stream()
                .map(h -> new HuespedDTO(h.getId(), h.getNombre(), h.getCorreo(), h.getDocumentoIdentidad()))
                .collect(Collectors.toList());
    }

    
    public HuespedDTO obtenerPorId(Long id) {
        Huesped huesped = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El huésped con ID " + id + " no está registrado."));
        
        return new HuespedDTO(huesped.getId(), huesped.getNombre(), huesped.getCorreo(), huesped.getDocumentoIdentidad());
    }


    public HuespedDTO actualizar(Long id, HuespedDTO dto) {
        Huesped huesped = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se puede actualizar. El huésped con ID " + id + " no existe."));

        huesped.setNombre(dto.getNombre());
        huesped.setCorreo(dto.getCorreo());
        huesped.setDocumentoIdentidad(dto.getDocumentoIdentidad());

        Huesped actualizado = repository.save(huesped);
        return new HuespedDTO(actualizado.getId(), actualizado.getNombre(), actualizado.getCorreo(), actualizado.getDocumentoIdentidad());
    }

    
    public void eliminar(Long id) {
        Huesped huesped = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se puede eliminar. El huésped con ID " + id + " no existe."));
        
        repository.delete(huesped);
    }
}