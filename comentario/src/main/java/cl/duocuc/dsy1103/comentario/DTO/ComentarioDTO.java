package cl.duocuc.dsy1103.comentario.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de Transferencia de Datos para la gestión de Comentarios y Reseñas")

public class ComentarioDTO {

@Schema(description = "ID único autogenerado del comentario", example = "1", readOnly = true)
    private Long id;

    @Schema(description = "ID de la habitación asociada", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID de la habitación es obligatorio")
    private Long habitacionId;

    @Schema(description = "ID del huésped que realiza la reseña", example = "55", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID del huésped es obligatorio")
    private Long huespedId;

    @Schema(description = "Texto descriptivo con la opinión del huésped", example = "Excelente servicio, la habitación estaba impecable y muy cómoda.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El texto del comentario no puede estar vacío")
    private String texto;

    
    @Schema(description = "Calificación otorgada a la habitación (Escala de 1 a 5)", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1 estrella")
    @Max(value = 5, message = "La calificación máxima es 5 estrellas")
    private Integer calificacion;
}


