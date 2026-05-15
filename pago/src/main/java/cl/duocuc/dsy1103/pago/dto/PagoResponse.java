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
    private Long reservationId;
    private Double amount;
    private String paymentMethod;
    private String status; // Ejemplo: APPROVED, REJECTED
    private LocalDateTime paymentDate;
}