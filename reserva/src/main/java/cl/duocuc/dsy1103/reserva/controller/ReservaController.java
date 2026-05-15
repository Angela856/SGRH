package cl.duocuc.dsy1103.reserva.controller;

import cl.duocuc.dsy1103.reserva.model.Reserva;
import cl.duocuc.dsy1103.reserva.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reserva")
public class ReservaController {
    @Autowired
    private ReservaService service;

    @GetMapping
    public List<Reserva> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public Reserva crear(@Valid @RequestBody Reserva reserva) {
        return service.crearReserva(reserva);
    }
}