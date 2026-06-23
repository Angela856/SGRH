package cl.duocuc.dsy1103.huesped.Controller;

import cl.duocuc.dsy1103.huesped.DTO.HuespedDTO;
import cl.duocuc.dsy1103.huesped.Service.HuespedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/huespedes")

public class HuespedController {

private final HuespedService service;

    HuespedController(HuespedService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HuespedDTO> crear(@RequestBody HuespedDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<HuespedDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}


