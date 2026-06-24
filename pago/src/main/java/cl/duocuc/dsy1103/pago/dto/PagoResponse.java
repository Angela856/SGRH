package cl.duocuc.dsy1103.pago.dto;

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
@Schema(description = "Objeto que contiene el comprobante y estado de una transacción procesada")
public class PagoResponse {

    @Schema(description = "ID único del registro de pago en la base de datos", example = "105")
    private Long id;

    @Schema(description = "ID de la reserva asociada a este pago", example = "1")
    private Long idReserva;

    @Schema(description = "Monto total procesado en la transacción", example = "65000.50")
    private Double monto;

    @Schema(description = "Método utilizado para efectuar el pago", example = "TRANSBANK")
    private String metodo;

    @Schema(description = "Estado actual de la transacción en la pasarela de pagos", example = "APROBADO")
    private String estado; 

    @Schema(description = "Fecha y hora exacta en la que se confirmó el pago", example = "2026-06-23T19:25:00")
    private LocalDateTime fechaPago;
}