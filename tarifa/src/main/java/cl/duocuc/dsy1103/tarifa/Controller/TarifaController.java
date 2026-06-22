package cl.duocuc.dsy1103.tarifa.Controller;
import cl.duocuc.dsy1103.tarifa.DTO.TarifaDTO;
import cl.duocuc.dsy1103.tarifa.Service.TarifaService;
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
@RequestMapping("/api/tarifa")
@Tag(name = "Tarifas", description = "Operaciones relacionadas con la administración y cálculo de costos por habitación")
public class TarifaController {

@Autowired
    private TarifaService service;

    @Operation(summary = "Crear una nueva tarifa", description = "Registra una tarifa asociada a un tipo de habitación con validaciones preventivas de precios.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "21", description = "Tarifa creada exitosamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarifaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o corruptos")
    })
    @PostMapping

    public ResponseEntity<TarifaDTO> crear(@Valid @RequestBody TarifaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @Operation(summary = "Obtener listado de tarifas", description = "Recupera todas las tarifas registradas en la base de datos de Laragon.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<TarifaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener tarifa por ID de habitación", description = "Busca la tarifa activa asignada a una habitación específica a través de llamadas distribuidas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarifa encontrada exitosamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarifaDTO.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró una tarifa asignada a esa habitación")
    })
    @GetMapping("/habitacion/{habitacionId}")
    public ResponseEntity<TarifaDTO> obtenerPorHabitacion(
        @Parameter(description = "ID numérico de la habitación a consultar", required = true, example = "1")
        @PathVariable Long habitacionId) {
        return ResponseEntity.ok(service.obtenerPorHabitacion(habitacionId));
    }
}

