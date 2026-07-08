package cl.duocuc.dsy1103.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Schema(description = "Datos necesarios para registrar o actualizar la información de una habitación de hotel")
public class HotelRequest {

    @NotBlank(message = "El nombre del hotel es requerido")
    @Size(max = 100, message = "El nombre debe tener máximo 100 caracteres")
    @Schema(description = "Nombre comercial o identificativo de la sucursal", example = "Hotel Duoc Plaza", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "La dirección es requerida")
    @Size(max = 255, message = "La dirección debe tener máximo 255 caracteres")
    @Schema(description = "Dirección física completa de la sucursal", example = "Av. España 1680, Valparaíso", requiredMode = Schema.RequiredMode.REQUIRED)
    private String direccion;

    @NotBlank(message = "La calificación por estrellas es requerida")
    @Size(max = 10, message = "Largo de estrellas inválido")
    @Schema(description = "Calificación de calidad del hotel en estrellas", example = "4 Estrellas", allowableValues = {"3 Estrellas", "4 Estrellas", "5 Estrellas"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String estrellas;

    @NotBlank(message = "El número o código de la habitación es requerido")
    @Schema(description = "Identificador interno de la habitación en la sucursal", example = "Suite 402", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numeroHabitacion;

    @NotNull(message = "El precio por noche es requerido")
    @Min(value = 0, message = "El precio por noche no puede ser negativo")
    @Schema(description = "Tarifa base cobrada por cada noche de alojamiento", example = "65000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precioPorNoche;

    @NotNull(message = "El estado de disponibilidad es requerido")
    @Schema(description = "Indica si la habitación está lista para ser reservada", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean disponible;
}