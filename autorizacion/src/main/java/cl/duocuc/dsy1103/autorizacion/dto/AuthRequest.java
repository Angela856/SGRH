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
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String correo;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    private String contrasena;

    @NotBlank(message = "Role is required")
    private String rol; // Ejemplo: ADMIN, RECEPCIONISTA, CLIENTE
}