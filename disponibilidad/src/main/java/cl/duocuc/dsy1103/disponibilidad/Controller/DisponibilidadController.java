package cl.duocuc.dsy1103.disponibilidad.Controller;

import cl.duocuc.dsy1103.disponibilidad.DTO.DisponibilidadDTO;
import cl.duocuc.dsy1103.disponibilidad.Service.DisponibilidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
@Tag(name = "Disponibilidad", description = "Endpoints para la gestión de calendarios, bloqueos y estados de ocupación de las habitaciones")



public class DisponibilidadController {

    private final DisponibilidadService service;

    DisponibilidadController(DisponibilidadService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar disponibilidad o bloqueo", description = "Asigna un rango de fechas y un estado (Disponible, Ocupada, Mantenimiento) a una habitación.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Disponibilidad registrada exitosamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DisponibilidadDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (Fechas inconsistentes o nulas)"),
        @ApiResponse(responseCode = "404", description = "La habitación especificada no existe en el sistema distribuido")
    })
    @PostMapping
    // CORRECCIÓN CRÍTICA: Quitamos el '?', agregamos @Valid y devolvemos de forma limpia un 201 Created
    public ResponseEntity<DisponibilidadDTO> crear(@Valid @RequestBody DisponibilidadDTO dto) {
        DisponibilidadDTO guardado = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @Operation(summary = "Obtener todo el historial de disponibilidad", description = "Recupera todas las asignaciones de fechas de la base de datos de Laragon.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de disponibilidad obtenido de manera exitosa")
    })
    @GetMapping
    public ResponseEntity<List<DisponibilidadDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}



