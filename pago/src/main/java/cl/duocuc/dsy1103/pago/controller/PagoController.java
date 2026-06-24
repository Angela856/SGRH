package cl.duocuc.dsy1103.pago.controller;

import cl.duocuc.dsy1103.pago.dto.PagoRequest;
import cl.duocuc.dsy1103.pago.dto.PagoResponse;
import cl.duocuc.dsy1103.pago.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
// 1. Definimos la sección para la interfaz de Swagger UI
@Tag(name = "Procesamiento de Pagos", description = "Endpoints para la gestión, consulta y creación de transacciones financieras del hotel")
public class PagoController {

    private final PagoService service;

    PagoController(PagoService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener historial de pagos", description = "Devuelve una lista con todas las transacciones financieras registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Historial obtenido con éxito")
    @GetMapping
    public List<PagoResponse> obtenerTodos() {
        return service.obtenerTodosLosPagos(); 
    }

    @Operation(summary = "Buscar pago por ID", description = "Obtiene los detalles de un pago específico a través de su identificador único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado y detallado con éxito"),
        @ApiResponse(responseCode = "404", description = "No existe ninguna transacción con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public PagoResponse obtenerPorId(
        @Parameter(description = "ID del registro de pago", example = "105") @PathVariable Long id
    ) {
        return service.obtenerPagoPorId(id);
    }

    @Operation(summary = "Procesar una nueva transacción", description = "Registra un pago asociado a una reserva válida. Se comunica internamente para validar la existencia de la reserva.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transacción procesada y registrada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del pago inválidos o saldo inconsistente"),
        @ApiResponse(responseCode = "422", description = "Error de negocio: La reserva asociada no existe o ya está pagada")
    })
    @PostMapping
    public PagoResponse procesar(@Valid @RequestBody PagoRequest request) {
        return service.crearPago(request); 
    }
}