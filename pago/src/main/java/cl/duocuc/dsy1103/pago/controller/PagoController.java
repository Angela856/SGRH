package cl.duocuc.dsy1103.pago.controller;

import cl.duocuc.dsy1103.pago.dto.PagoRequest;
import cl.duocuc.dsy1103.pago.dto.PagoResponse;
import cl.duocuc.dsy1103.pago.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService service;

    PagoController(PagoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PagoResponse> obtenerTodos() {
        return service.obtenerTodosLosPagos(); 
    }

    @GetMapping("/{id}")
    public PagoResponse obtenerPorId(@PathVariable Long id) {
        return service.obtenerPagoPorId(id);
    }

    @PostMapping
    public PagoResponse procesar(@Valid @RequestBody PagoRequest request) {
        return service.crearPago(request); 
    }
}