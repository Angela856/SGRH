package cl.duocuc.dsy1103.tarifa.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de Transferencia de Datos para la gestión de Tarifas")

public class TarifaDTO {
@Schema(description = "ID único autogenerado de la tarifa", example = "1", readOnly = true)
    private Long id;

   
    @Schema(description = "ID de la habitación asociada en el microservicio de Habitaciones", example = "105", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID de la habitación es obligatorio")
    private Long habitacionId;

   
    @Schema(description = "Precio por noche de estadía en pesos chilenos (CLP)", example = "45000.0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El precio por noche es obligatorio")
    @DecimalMin(value = "0.1", message = "El precio por noche debe ser mayor a cero")
    private Double precioNoche;

    
    @Schema(description = "Temporada del año aplicable a la tarifa", example = "ALTA", allowableValues = {"ALTA", "MEDIA", "BAJA"}, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La temporada es obligatoria")
    private String temporada;    
}