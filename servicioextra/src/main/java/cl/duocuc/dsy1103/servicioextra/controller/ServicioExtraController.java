package cl.duocuc.dsy1103.servicioextra.controller;

import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraRequest;
import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraResponse;
import cl.duocuc.dsy1103.servicioextra.service.ServicioExtraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicios-extras")
@Tag(name = "Servicios Extras", description = "Endpoints para la administración y asignación de consumos o amenidades adicionales")
public class ServicioExtraController {

    private final ServicioExtraService service;

    ServicioExtraController(ServicioExtraService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los servicios extras", description = "Recupera el catálogo completo de consumos y servicios adicionales registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    @GetMapping
    public List<ServicioExtraResponse> listarTodos() {
        return service.obtenerTodosLosServicios();
    }

    @Operation(summary = "Buscar servicio extra por ID", description = "Obtiene el detalle de un consumo específico mediante su identificador único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio adicional localizado con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID del servicio extra no existe")
    })
    @GetMapping("/{id}")
    public ServicioExtraResponse obtenerPorId(
        @Parameter(description = "ID del servicio extra a consultar", example = "5") @PathVariable Long id
    ) {
        return service.obtenerServicioPorId(id);
    }

    @Operation(summary = "Asignar un servicio extra a una reserva", description = "Registra un nuevo consumo (ej: Spa, alimentación) asociándolo obligatoriamente al ID de una reserva existente en la URL.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Servicio extra cargado a la reserva exitosamente"),
        @ApiResponse(responseCode = "400", description = "Estructura del cuerpo de la solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "La reserva especificada en la URL no existe en el sistema central")
    })
    @PostMapping("/reserva/{idReserva}")
    public ServicioExtraResponse crear(
        @Valid @RequestBody ServicioExtraRequest request, 
        @Parameter(description = "ID de la reserva activa a la cual se le cargará el costo", example = "1001") @PathVariable Long idReserva
    ) {
        return service.crearServicioExtra(request, idReserva);
    }

    @Operation(summary = "Actualizar datos de un servicio extra", description = "Modifica la información, precio o descripción de un servicio adicional basándose en su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos actualizados correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró el servicio extra para modificar")
    })
    @PutMapping("/{id}")
    public ServicioExtraResponse actualizar(
        @Parameter(description = "ID del servicio extra a modificar", example = "5") @PathVariable Long id, 
        @Valid @RequestBody ServicioExtraRequest request
    ) {
        return service.actualizarServicioExtra(id, request);
    }

    @Operation(summary = "Eliminar un servicio extra", description = "Remueve permanentemente el registro de un servicio extra o consumo del sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Registro eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "El servicio extra que se intenta eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public void eliminar(
        @Parameter(description = "ID del servicio extra a dar de baja", example = "5") @PathVariable Long id
    ) {
        service.eliminarServicioExtra(id);
    }
}