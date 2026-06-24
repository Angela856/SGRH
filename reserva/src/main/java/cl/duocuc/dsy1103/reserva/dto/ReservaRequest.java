package cl.duocuc.dsy1103.reserva.dto;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Schema(description = "Datos necesarios para solicitar la creación o modificación de una reserva de habitación")
public class ReservaRequest {

    @NotNull(message = "El id de usuario es requerido")
    @Schema(description = "ID del usuario/cliente que realiza la reserva (Validado contra el servicio de Autorización)", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idUsuario;

    @NotNull(message = "El id de habitación es requerido")
    @Schema(description = "ID de la habitación que se desea reservar (Validado contra el servicio de Hotel)", example = "105", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idHabitacion;

    @NotNull(message = "La fecha de inicio es requerida")
    @Schema(description = "Fecha de check-in (ingreso al hotel)", example = "2026-07-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es requerida")
    @Schema(description = "Fecha de check-out (salida del hotel)", example = "2026-07-05", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaFin;
}