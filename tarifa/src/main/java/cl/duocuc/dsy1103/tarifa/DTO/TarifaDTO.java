package cl.duocuc.dsy1103.tarifa.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TarifaDTO {
private Long id;
private Long habitacionId;
private double precioNoche;
private String temporada;    

}
