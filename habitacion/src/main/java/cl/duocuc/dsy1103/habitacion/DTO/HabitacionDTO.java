package cl.duocuc.dsy1103.habitacion.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HabitacionDTO {
private Long id;
    private Long hotelId;
    private String numero;
    private String tipo;
    private double precioBase;
}