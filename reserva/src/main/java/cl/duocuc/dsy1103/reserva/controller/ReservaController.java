package cl.duocuc.dsy1103.reserva.controller;

import cl.duocuc.dsy1103.reserva.dto.ReservaRequest;
import cl.duocuc.dsy1103.reserva.dto.ReservaResponse;
import cl.duocuc.dsy1103.reserva.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping
    public List<ReservaResponse> obtenerTodas() {
        return service.obtenerTodasLasReservas();
    }

    @GetMapping("/{id}")
    public ReservaResponse obtenerPorId(@PathVariable Long id) {
        return service.obtenerReservaPorId(id);
    }

    @PostMapping
    public ReservaResponse crear(@Valid @RequestBody ReservaRequest request) {
        return service.crearReserva(request);
    }

    @PutMapping("/{id}")
    public ReservaResponse actualizar(@PathVariable Long id, @Valid @RequestBody ReservaRequest request) {
        return service.actualizarReserva(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarReserva(id);
    }
}