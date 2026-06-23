package cl.duocuc.dsy1103.autorizacion.controller;

import cl.duocuc.dsy1103.autorizacion.dto.AuthRequest;
import cl.duocuc.dsy1103.autorizacion.dto.AuthResponse;
import cl.duocuc.dsy1103.autorizacion.service.AutorizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@RequestMapping("/api/auth")
// 1. Tag agrupa todos estos endpoints bajo una sección bonita en la interfaz de Swagger
@Tag(name = "Autenticación y Usuarios", description = "Endpoints para el registro, login y gestión de credenciales de usuarios")
public class AutorizacionController {
    
    private final AutorizacionService service;

    AutorizacionController(AutorizacionService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista completa con la información pública de los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    @GetMapping
    public List<AuthResponse> obtenerTodos() {
        return service.obtenerTodosLosUsuarios();
    }

    @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario específico en el sistema mediante su identificador numérico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "El usuario con el ID proporcionado no existe")
    })
    @GetMapping("/{id}")
    public AuthResponse obtenerPorId(
        @Parameter(description = "ID único del usuario a consultar", example = "1") @PathVariable Long id
    ) {
        return service.obtenerUsuarioPorId(id);
    }

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un usuario en el sistema. Valida que el correo y la contraseña cumplan con los requisitos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. correo mal formado o campos vacíos)")
    })
    @PostMapping("/registrar") 
    public AuthResponse registrar(@Valid @RequestBody AuthRequest request) { 
        return service.crearUsuario(request); 
    }

    @Operation(summary = "Iniciar sesión (Login)", description = "Verifica las credenciales del usuario y permite el acceso al sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación procesada (Revisar el cuerpo del texto para ver si fue 'Ok' o 'Error')"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas") // Es buena práctica mapear esto a futuro
    })
    @PostMapping("/login") 
    public String login(@RequestBody AuthRequest request) { 
        return service.login(request.getCorreo(), request.getContrasena()) ? "Ok" : "Error"; 
    }
}
