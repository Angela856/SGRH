package cl.duocuc.dsy1103.pago.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para procesar y registrar una transacción financiera")
public class PagoRequest {

    @NotNull(message = "Se requiere el ID de la reserva")
    @Schema(description = "Identificador único de la reserva asociada al pago", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idReserva;

    @NotNull(message = "Se requiere la cantidad")
    @Positive(message = "La cantidad debe ser mayor que cero")
    @Schema(description = "Monto total a cobrar por la transacción", example = "65000.50", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double monto;

    @NotBlank(message = "Se requiere método de pago")
    // Usamos allowableValues para dar opciones claras en la interfaz de usuario de Swagger
    @Schema(description = "Canal o medio utilizado para efectuar el pago", example = "TRANSBANK", allowableValues = {"TRANSBANK", "PAYPAL", "EFECTIVO", "TRANSFERENCIA"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String metodo; 
}