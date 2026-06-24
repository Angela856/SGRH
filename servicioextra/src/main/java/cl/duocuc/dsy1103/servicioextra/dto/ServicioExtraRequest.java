package cl.duocuc.dsy1103.servicioextra.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos obligatorios para registrar un consumo o cargo por servicio adicional")
public class ServicioExtraRequest {

    @NotBlank(message = "Se requiere descripción")
    @Size(max = 255, message = "La descripción debe tener como máximo 255 caracteres")
    @Schema(description = "Detalle o nombre del servicio complementario solicitado", example = "Acceso al Spa y Masaje Relajante", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @NotNull(message = "Se requiere precio")
    @Positive(message = "El precio debe ser mayor que cero")
    @Schema(description = "Costo neto asociado a la prestación del servicio", example = "35000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precio;
}