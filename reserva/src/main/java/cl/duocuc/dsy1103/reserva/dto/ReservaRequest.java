package cl.duocuc.dsy1103.reserva.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class ReservaRequest {
    @NotNull(message = "El id de usuario es requerido")
    private Long idUsuario;

    @NotNull(message = "El id de habitación es requerido")
    private Long idHabitacion;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es requerida")
    private LocalDate fechaFin;
}