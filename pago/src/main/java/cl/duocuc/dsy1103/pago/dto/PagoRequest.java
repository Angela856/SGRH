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
    @NotNull(message = "Se requiere el ID de la reserva")
    private Long idReserva;

    @NotNull(message = "Se requiere la cantidad")
    @Positive(message = "La cantidad debe ser mayor que cero")
    private Double monto;

    @NotBlank(message = "Se requiere método de pago")
    private String metodo; // Ejemplo: TRANSBANK, PAYPAL
}