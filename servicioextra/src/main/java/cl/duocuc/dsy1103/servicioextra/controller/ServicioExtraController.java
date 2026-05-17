package cl.duocuc.dsy1103.servicioextra.controller;

import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraRequest;
import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraResponse;
import cl.duocuc.dsy1103.servicioextra.service.ServicioExtraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicios-extras")
public class ServicioExtraController {

    @Autowired
    private ServicioExtraService service;

    @GetMapping
    public List<ServicioExtraResponse> listarTodos() {
        return service.obtenerTodosLosServicios();
    }

    @GetMapping("/{id}")
    public ServicioExtraResponse obtenerPorId(@PathVariable Long id) {
        return service.obtenerServicioPorId(id);
    }

    // Nota que el POST recibe el id de la reserva como parámetro en la URL
    @PostMapping("/reserva/{idReserva}")
    public ServicioExtraResponse crear(@Valid @RequestBody ServicioExtraRequest request, @PathVariable Long idReserva) {
        return service.crearServicioExtra(request, idReserva);
    }

    @PutMapping("/{id}")
    public ServicioExtraResponse actualizar(@PathVariable Long id, @Valid @RequestBody ServicioExtraRequest request) {
        return service.actualizarServicioExtra(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarServicioExtra(id);
    }
}