package cl.duocuc.dsy1103.habitacion.Controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duocuc.dsy1103.habitacion.Model.Habitacion;
import cl.duocuc.dsy1103.habitacion.Service.HabitacionService;

import java.util.List;

@RestController
@RequestMapping("/habitaciones")

public class HabitacionController {

@Autowired
    private HabitacionService service;

    @PostMapping
    public ResponseEntity<Habitacion> crear(@Valid @RequestBody Habitacion habitacion) {
        return new ResponseEntity<>(service.guardar(habitacion), HttpStatus.CREATED);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Habitacion>> porHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(service.listarPorHotel(hotelId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> porId(@PathVariable Long id) {
        Habitacion h = service.buscarPorId(id);
        return h != null ? ResponseEntity.ok(h) : ResponseEntity.notFound().build();
    }



}
