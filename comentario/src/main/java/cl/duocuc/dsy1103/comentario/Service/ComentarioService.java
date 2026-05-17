package cl.duocuc.dsy1103.comentario.Service;
import cl.duocuc.dsy1103.comentario.DTO.ComentarioDTO;
import cl.duocuc.dsy1103.comentario.Model.Comentario;
import cl.duocuc.dsy1103.comentario.Repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ComentarioService {

 @Autowired
    private ComentarioRepository repository;

    public ComentarioDTO guardar(ComentarioDTO dto) {
        Comentario comentario = new Comentario();
        comentario.setHabitacionId(dto.getHabitacionId());
        comentario.setHuespedId(dto.getHuespedId());
        comentario.setTexto(dto.getTexto());
        comentario.setCalificacion(dto.getCalificacion());

        Comentario guardado = repository.save(comentario);
        return new ComentarioDTO(guardado.getId(), guardado.getHabitacionId(), guardado.getHuespedId(), guardado.getTexto(), guardado.getCalificacion());
    }

    public List<ComentarioDTO> listar() {
        return repository.findAll().stream()
                .map(c -> new ComentarioDTO(c.getId(), c.getHabitacionId(), c.getHuespedId(), c.getTexto(), c.getCalificacion()))
                .collect(Collectors.toList());
    }

    public List<ComentarioDTO> listarPorHabitacion(Long habitacionId) {
        return repository.findByHabitacionId(habitacionId).stream()
                .map(c -> new ComentarioDTO(c.getId(), c.getHabitacionId(), c.getHuespedId(), c.getTexto(), c.getCalificacion()))
                .collect(Collectors.toList());
    }
}   

