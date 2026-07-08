package cl.duocuc.dsy1103.hotel.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Objeto que contiene los detalles públicos de una habitación y su sucursal hotelera")
public class HotelResponse {

    @Schema(description = "Identificador único del registro (ID de Habitación) en la base de datos", example = "1")
    private Long id;

    @Schema(description = "Nombre registrado de la sucursal", example = "Hotel Duoc Plaza")
    private String nombre;

    @Schema(description = "Ubicación física o dirección del hotel", example = "Av. España 1680, Valparaíso")
    private String direccion;

    @Schema(description = "Categoría o estrellas asignadas al hotel", example = "4 Estrellas")
    private String estrellas;

    @Schema(description = "Número o código identificador de la habitación", example = "Suite 402")
    private String numeroHabitacion;

    @Schema(description = "Precio por noche de estadía en esta unidad", example = "65000.50")
    private Double precioPorNoche;

    @Schema(description = "Determina si la unidad se encuentra libre para operar", example = "true")
    private Boolean disponible;

    @Schema(description = "Fecha y hora en la que se registró el alojamiento en el sistema", example = "2026-06-23T19:00:00")
    private LocalDateTime createdAt;
}