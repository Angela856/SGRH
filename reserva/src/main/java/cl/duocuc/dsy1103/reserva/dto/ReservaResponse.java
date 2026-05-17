package cl.duocuc.dsy1103.reserva.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservaResponse {
    private Long id;
    private Long idUsuario;
    private Long idHabitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private LocalDateTime createdAt;
}