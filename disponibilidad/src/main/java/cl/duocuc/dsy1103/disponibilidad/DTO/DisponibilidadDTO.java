package cl.duocuc.dsy1103.disponibilidad.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DisponibilidadDTO {

private Long id;
private Long habitacionId;
private boolean disponible;
}    


