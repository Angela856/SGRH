package cl.duocuc.dsy1103.servicioextra.controller;

import cl.duocuc.dsy1103.servicioextra.model.ServicioExtra;
import cl.duocuc.dsy1103.servicioextra.service.ServicioExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicio-extra")
public class ServicioExtraController {
    @Autowired
    private ServicioExtraService service;

    @GetMapping
    public List<ServicioExtra> listar() {
        return service.listarServicios();
    }
}
