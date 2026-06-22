package cl.duocuc.dsy1103.disponibilidad.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de Transferencia de Datos para el control de Disponibilidad de Habitaciones")

public class DisponibilidadDTO {
@Schema(description = "ID único autogenerado del registro de disponibilidad", example = "1", readOnly = true)
    private Long id;

    @Schema(description = "ID de la habitación asociada", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID de la habitación es obligatorio")
    private Long habitacionId;

    @Schema(description = "Estado de disponibilidad de la habitación (true = Disponible, false = Ocupada/Bloqueada)", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El estado de disponibilidad es obligatorio (true o false)")
    private Boolean disponible;
}
