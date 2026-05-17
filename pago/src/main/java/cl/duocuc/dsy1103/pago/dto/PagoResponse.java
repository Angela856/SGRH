package cl.duocuc.dsy1103.pago.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponse {
    private Long id;
    private Long idReserva;
    private Double monto;
    private String metodo;
    private String estado; // Ejemplo: APPROVED, REJECTED
    private LocalDateTime fechaPago;
}