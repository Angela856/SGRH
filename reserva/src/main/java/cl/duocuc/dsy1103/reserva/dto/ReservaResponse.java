package cl.duocuc.dsy1103.reserva.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Objeto de respuesta que contiene la información detallada de una reserva confirmada")
public class ReservaResponse {

    @Schema(description = "Identificador único de la reserva en la base de datos", example = "1")
    private Long id;

    @Schema(description = "ID del usuario o cliente que realizó la reserva", example = "12")
    private Long idUsuario;

    @Schema(description = "ID de la habitación reservada", example = "105")
    private Long idHabitacion;

    @Schema(description = "Fecha de ingreso programada (Check-in)", example = "2026-07-01")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de salida programada (Check-out)", example = "2026-07-05")
    private LocalDate fechaFin;

    @Schema(description = "Estado administrativo actual de la reserva", example = "CONFIRMADA")
    private String estado;
    @Schema(description = "Fecha y hora de creación de la reserva", example = "2026-06-23T19:40:00")
    private LocalDateTime createdAt;
}