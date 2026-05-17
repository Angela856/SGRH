package cl.duocuc.dsy1103.autorizacion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthRequest {
    @NotBlank(message = "El correo es necesario")
    @Email(message = "El correo es invalido")
    @Size(max = 100, message = "El correo debe tener como máximo 100 caracteres")
    private String correo;

    @NotBlank(message = "Se requiere contraseña")
    @Size(min = 6, max = 255, message = "La contraseña debe tener entre 6 y 255 caracteres")
    private String contrasena;

    @NotBlank(message = "Se requiere rol")
    private String rol; // Ejemplo: ADMIN, RECEPCIONISTA, CLIENTE
}