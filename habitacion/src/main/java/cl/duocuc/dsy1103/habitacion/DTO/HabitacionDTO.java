package cl.duocuc.dsy1103.habitacion.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de Transferencia de Datos para la gestión de Habitaciones")

public class HabitacionDTO {
@Schema(description = "ID único de la habitación generado en Laragon", example = "1", readOnly = true)
    private Long id;

    @Schema(description = "ID del hotel al que pertenece la habitación", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID del hotel es un campo obligatorio")
    private Long hotelId;

    @Schema(description = "Número o identificador físico de la habitación", example = "305-A", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El número de habitación no puede estar vacío")
    private String numero;

    @Schema(description = "Tipo o categoría de la habitación (Simple, Doble, Suite)", example = "Suite Executive", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El tipo de habitación no puede estar vacío")
    private String tipo;

    // CORRECCIÓN CRÍTICA: Cambiado de double a Double (Wrapper) para permitir validaciones estrictas
    @Schema(description = "Precio base por noche de la habitación", example = "75000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio base debe ser un valor mayor a cero")
    private Double precioBase;
}