package cl.duocuc.dsy1103.pago.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PagoRequest {
    @NotNull(message = "Reservation ID is required")
    private Long idReserva;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double monto;

    @NotBlank(message = "Payment method is required")
    private String metodo; // Ejemplo: TRANSBANK, PAYPAL
}