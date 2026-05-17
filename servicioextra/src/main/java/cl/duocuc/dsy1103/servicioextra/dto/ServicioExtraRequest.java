package cl.duocuc.dsy1103.servicioextra.dto;

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

public class ServicioExtraRequest {
    @NotBlank(message = "Se requiere descripción")
    @Size(max = 255, message = "La descripción debe tener como máximo 255 caracteres")
    private String descripcion;

    @NotNull(message = "Se requiere precio")
    @Positive(message = "El precio debe ser mayor que cero")
    private Double precio;
}