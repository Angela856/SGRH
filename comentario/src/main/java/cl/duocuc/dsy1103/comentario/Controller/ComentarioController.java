package cl.duocuc.dsy1103.comentario.Controller;

import cl.duocuc.dsy1103.comentario.DTO.ComentarioDTO;
import cl.duocuc.dsy1103.comentario.Service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentario")

public class ComentarioController {

    @Autowired
    private ComentarioService service;

    @PostMapping
    public ResponseEntity<ComentarioDTO> crear(@RequestBody ComentarioDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/habitacion/{habitacionId}")
    public ResponseEntity<List<ComentarioDTO>> obtenerPorHabitacion(@PathVariable Long habitacionId) {
        return ResponseEntity.ok(service.listarPorHabitacion(habitacionId));
    }
}

