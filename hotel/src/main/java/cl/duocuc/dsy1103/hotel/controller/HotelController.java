package cl.duocuc.dsy1103.hotel.controller;

import cl.duocuc.dsy1103.hotel.dto.HotelRequest;
import cl.duocuc.dsy1103.hotel.dto.HotelResponse;
import cl.duocuc.dsy1103.hotel.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hoteles")
// 1. Agrupamos los endpoints bajo una sección clara
@Tag(name = "Gestión de Hoteles", description = "Endpoints para realizar operaciones CRUD sobre las sucursales del hotel")
public class HotelController {

    private final HotelService service;

    HotelController(HotelService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas las sucursales de hoteles", description = "Devuelve una lista con todos los hoteles registrados en la cadena.")
    @ApiResponse(responseCode = "200", description = "Lista de hoteles obtenida con éxito")
    @GetMapping
    public List<HotelResponse> obtenerTodos() {
        return service.obtenerTodosLosHoteles();
    }

    @Operation(summary = "Obtener un hotel por su ID", description = "Busca una sucursal específica utilizando su identificador único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotel encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "El hotel con el ID indicado no existe")
    })
    @GetMapping("/{id}")
    public HotelResponse obtenerPorId(
        @Parameter(description = "ID del hotel a consultar", example = "1") @PathVariable Long id
    ) {
        return service.obtenerHotelPorId(id);
    }

    @Operation(summary = "Registrar un nuevo hotel", description = "Crea una nueva sucursal en el sistema. Valida que los campos obligatorios vengan en el cuerpo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Hotel creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes")
    })
    @PostMapping
    public HotelResponse crear(@Valid @RequestBody HotelRequest request) {
        return service.crearHotel(request);
    }

    @Operation(summary = "Actualizar un hotel existente", description = "Modifica los datos de una sucursal basándose en su ID y los nuevos datos provistos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotel actualizado con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos"),
        @ApiResponse(responseCode = "404", description = "No se encontró el hotel para actualizar")
    })
    @PutMapping("/{id}")
    public HotelResponse actualizar(
        @Parameter(description = "ID del hotel que se desea modificar", example = "1") @PathVariable Long id, 
        @Valid @RequestBody HotelRequest request
    ) {
        return service.actualizarHotel(id, request);
    }

    @Operation(summary = "Eliminar un hotel", description = "Remueve permanentemente una sucursal del sistema mediante su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Hotel eliminado con éxito (No devuelve contenido)"),
        @ApiResponse(responseCode = "404", description = "El hotel que se intenta eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public void eliminar(
        @Parameter(description = "ID del hotel a eliminar", example = "1") @PathVariable Long id
    ) {
        service.eliminarHotel(id);
    }
}