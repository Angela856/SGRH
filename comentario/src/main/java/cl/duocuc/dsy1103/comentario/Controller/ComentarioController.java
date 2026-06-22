package cl.duocuc.dsy1103.comentario.Controller;

import cl.duocuc.dsy1103.comentario.DTO.ComentarioDTO;
import cl.duocuc.dsy1103.comentario.Service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/comentario")
// Agrupa el endpoint bajo el módulo correspondiente en la UI de Swagger
@Tag(name = "Comentarios", description = "Operaciones relacionadas con las reseñas y calificaciones de las habitaciones")

public class ComentarioController {
@Autowired
    private ComentarioService service;

    @Operation(summary = "Crear un nuevo comentario", description = "Registra una reseña y una calificación de estrellas para una habitación específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comentario creado exitosamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComentarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (calificación fuera de rango 1-5 o campos vacíos)")
    })
    @PostMapping
    // CORRECCIÓN: Se agrega @Valid para interceptar errores y HttpStatus.CREATED para una semántica REST correcta
    public ResponseEntity<ComentarioDTO> crear(@Valid @RequestBody ComentarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @Operation(summary = "Obtener el listado global de comentarios", description = "Recupera todas las reseñas almacenadas en la base de datos de Laragon.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado global obtenido exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener comentarios por ID de habitación", description = "Filtra e historial de reseñas asociadas a una habitación en específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentarios de la habitación obtenidos exitosamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComentarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada o sin registros")
    })
    @GetMapping("/habitacion/{habitacionId}")
    public ResponseEntity<List<ComentarioDTO>> obtenerPorHabitacion(
        @Parameter(description = "ID numérico de la habitación a consultar", required = true, example = "101")
        @PathVariable Long habitacionId) {
        return ResponseEntity.ok(service.listarPorHabitacion(habitacionId));
    }
}