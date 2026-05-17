package cl.duocuc.dsy1103.autorizacion.controller;

import cl.duocuc.dsy1103.autorizacion.dto.AuthRequest;
import cl.duocuc.dsy1103.autorizacion.dto.AuthResponse;
import cl.duocuc.dsy1103.autorizacion.service.AutorizacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@RequestMapping("/api/auth")
public class AutorizacionController {
    
    @Autowired 
    private AutorizacionService service;

    @GetMapping
    public List<AuthResponse> obtenerTodos() {
        return service.obtenerTodosLosUsuarios();
    }

    @GetMapping("/{id}")
    public AuthResponse obtenerPorId(@PathVariable Long id) {
        return service.obtenerUsuarioPorId(id);
    }

    @PostMapping("/registrar") 
    public AuthResponse registrar(@Valid @RequestBody AuthRequest request) { 
        return service.crearUsuario(request); 
    }

    @PostMapping("/login") 
    public String login(@RequestBody AuthRequest request) { 
        return service.login(request.getCorreo(), request.getContrasena()) ? "Ok" : "Error"; 
    }
}
