package cl.duocuc.dsy1103.servicioextra.dto;

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
@Schema(description = "Objeto de respuesta que contiene el comprobante de un servicio adicional registrado")
public class ServicioExtraResponse {

    @Schema(description = "Identificador único del registro de servicio extra en la base de datos", example = "5")
    private Long id;

    @Schema(description = "Detalle o nombre de la amenidad o consumo solicitado", example = "Acceso al Spa y Masaje Relajante")
    private String descripcion;

    @Schema(description = "Monto total cobrado por la prestación del servicio", example = "35000.00")
    private Double precio;

    @Schema(description = "ID de la reserva a la cual se le carga este servicio adicional", example = "10")
    private Long idReserva;

    @Schema(description = "Fecha y hora exacta en la que se ingresó el consumo al sistema", example = "2026-06-23T20:15:00")
    private LocalDateTime createdAt;
}