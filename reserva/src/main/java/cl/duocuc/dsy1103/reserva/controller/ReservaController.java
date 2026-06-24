package cl.duocuc.dsy1103.reserva.controller;

import cl.duocuc.dsy1103.reserva.dto.ReservaRequest;
import cl.duocuc.dsy1103.reserva.dto.ReservaResponse;
import cl.duocuc.dsy1103.reserva.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
// 1. Agrupamos y le damos identidad en el Swagger UI
@Tag(name = "Core de Reservas", description = "Endpoints principales para la gestión del ciclo de vida de las reservas hoteleras")
public class ReservaController {

    private final ReservaService service;

    ReservaController(ReservaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas las reservas", description = "Recupera un historial completo de todas las reservas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente")
    @GetMapping
    public List<ReservaResponse> obtenerTodas() {
        return service.obtenerTodasLasReservas();
    }

    @Operation(summary = "Obtener reserva por su ID", description = "Busca una reserva en particular mediante su identificador único de base de datos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva localizada con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró ninguna reserva con el ID especificado")
    })
    @GetMapping("/{id}")
    public ReservaResponse obtenerPorId(
        @Parameter(description = "ID único de la reserva", example = "1") @PathVariable Long id
    ) {
        return service.obtenerReservaPorId(id);
    }

    @Operation(summary = "Crear una nueva reserva", description = "Registra una reserva en el sistema. Internamente valida mediante RestClient que el ID del usuario exista en 'autorizacion-service' y que la habitación exista en 'hotel-service'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reserva creada y confirmada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o formato de fecha erróneo"),
        @ApiResponse(responseCode = "404", description = "Error de validación externa: El usuario o la habitación no existen en sus respectivos servicios")
    })
    @PostMapping
    public ReservaResponse crear(@Valid @RequestBody ReservaRequest request) {
        return service.crearReserva(request);
    }

    @Operation(summary = "Modificar una reserva existente", description = "Actualiza los datos básicos (fechas, usuario o habitación) de una reserva en base a su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva actualizada de manera exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de actualización erróneos"),
        @ApiResponse(responseCode = "404", description = "La reserva que se intenta modificar no existe")
    })
    @PutMapping("/{id}")
    public ReservaResponse actualizar(
        @Parameter(description = "ID de la reserva a modificar", example = "1") @PathVariable Long id, 
        @Valid @RequestBody ReservaRequest request
    ) {
        return service.actualizarReserva(id, request);
    }

    @Operation(summary = "Anular / Eliminar una reserva", description = "Elimina permanentemente el registro de la reserva del hotel usando su identificador.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reserva eliminada con éxito del sistema"),
        @ApiResponse(responseCode = "404", description = "La reserva que se intenta eliminar no fue encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminar(
        @Parameter(description = "ID de la reserva a dar de baja", example = "1") @PathVariable Long id
    ) {
        service.eliminarReserva(id);
    }
}