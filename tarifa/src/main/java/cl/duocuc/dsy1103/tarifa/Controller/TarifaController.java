package cl.duocuc.dsy1103.tarifa.Controller;

import cl.duocuc.dsy1103.tarifa.DTO.TarifaDTO;
import cl.duocuc.dsy1103.tarifa.Service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifa")

public class TarifaController {

@Autowired
    private TarifaService service;

    @PostMapping
    public ResponseEntity<TarifaDTO> crear(@RequestBody TarifaDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

   
    @GetMapping
    public ResponseEntity<List<TarifaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

   
    @GetMapping("/habitacion/{habitacionId}")
    public ResponseEntity<TarifaDTO> obtenerPorHabitacion(@PathVariable Long habitacionId) {
        return ResponseEntity.ok(service.obtenerPorHabitacion(habitacionId));
    }
}    


