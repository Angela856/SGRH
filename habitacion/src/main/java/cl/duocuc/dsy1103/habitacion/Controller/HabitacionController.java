package cl.duocuc.dsy1103.habitacion.Controller;
import cl.duocuc.dsy1103.habitacion.DTO.HabitacionDTO;
import cl.duocuc.dsy1103.habitacion.Service.HabitacionService;
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
@RequestMapping("/api/habitacion")
@Tag(name = "Habitación", description = "Endpoints para la gestión, registro y consulta de habitaciones del complejo hotelero")

public class HabitacionController {
private final HabitacionService service;

    HabitacionController(HabitacionService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar una nueva habitación", description = "Crea una pieza hotelera enlazada a un hotel específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Habitación creada exitosamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = HabitacionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes")
    })
    @PostMapping
    public ResponseEntity<HabitacionDTO> crear(@Valid @RequestBody HabitacionDTO dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar habitaciones por Hotel", description = "Recupera todas las habitaciones que pertenecen al ID de un hotel determinado.")
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<HabitacionDTO>> porHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(service.listarPorHotel(hotelId));
    }

    @Operation(summary = "Buscar habitación por ID", description = "Endpoint clave invocado por OpenFeign para validar la existencia de la habitación.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Habitación encontrada con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID de la habitación no existe en Laragon")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabitacionDTO> porId(@PathVariable Long id) {
        // La lógica de lanzar el 404 estructurado se delega al Service
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}