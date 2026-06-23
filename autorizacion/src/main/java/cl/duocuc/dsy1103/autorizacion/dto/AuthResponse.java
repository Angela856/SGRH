package cl.duocuc.dsy1103.autorizacion.dto;

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
// @Schema a nivel de clase le da un nombre y descripción al modelo global en Swagger
@Schema(description = "Objeto que contiene la información pública del usuario tras una operación exitosa")
public class AuthResponse {

    @Schema(description = "ID único del usuario asignado en la base de datos", example = "1")
    private Long id;

    @Schema(description = "Correo electrónico del usuario registrado", example = "alumno@duocuc.cl")
    private String correo;

    @Schema(description = "Rol o perfil de acceso asignado al usuario", example = "ADMIN")
    private String rol;

    @Schema(description = "Fecha y hora exacta en la que se creó el usuario", example = "2026-06-23T15:30:00")
    private LocalDateTime createdAt;
}