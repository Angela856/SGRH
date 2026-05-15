package cl.duocuc.dsy1103.autorizacion.controller;

import cl.duocuc.dsy1103.autorizacion.model.Usuario;
import cl.duocuc.dsy1103.autorizacion.service.AutorizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AutorizacionController {
    @Autowired private AutorizacionService service;
    @PostMapping("/registrar") public Usuario reg(@RequestBody Usuario u) { return service.registrar(u); }
    @PostMapping("/login") public String login(@RequestBody Usuario u) { 
        return service.login(u.getCorreo(), u.getContrasena()) ? "Ok" : "Error"; 
    }
}
