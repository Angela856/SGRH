package cl.duocuc.dsy1103.pago.controller;

import cl.duocuc.dsy1103.pago.model.Pago;
import cl.duocuc.dsy1103.pago.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pago")
public class PagoController {
    @Autowired
    private PagoService service;

    @PostMapping
    public Pago procesar(@Valid @RequestBody Pago pago) {
        return service.procesarPago(pago);
    }
}
