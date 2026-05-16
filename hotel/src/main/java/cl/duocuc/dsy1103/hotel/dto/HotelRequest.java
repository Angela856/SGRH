package cl.duocuc.dsy1103.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class HotelRequest {
    @NotBlank(message = "El nombre del hotel es requerido")
    @Size(max = 100, message = "El nombre debe tener máximo 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección es requerida")
    @Size(max = 255, message = "La dirección debe tener máximo 255 caracteres")
    private String direccion;

    @NotBlank(message = "La calificación por estrellas es requerida")
    @Size(max = 10, message = "Largo de estrellas inválido")
    private String estrellas;
}