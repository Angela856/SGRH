package cl.duocuc.dsy1103.disponibilidad.Controller;
import cl.duocuc.dsy1103.disponibilidad.DTO.DisponibilidadDTO;
import cl.duocuc.dsy1103.disponibilidad.Service.DisponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")

public class DisponibilidadController {
@Autowired
    private DisponibilidadService service;

    // Crear nueva disponibilidad usando DTO
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody DisponibilidadDTO dto) {
        DisponibilidadDTO guardado = service.guardar(dto);
        
        if (guardado != null) {
            return ResponseEntity.ok(guardado);
        } else {
            // Retornamos un 404 si la validación en el Service falló
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No se pudo crear. La habitación no existe o hubo un error.");
        }
    }

    
    @GetMapping
    public ResponseEntity<List<DisponibilidadDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}



