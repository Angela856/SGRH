package cl.duocuc.dsy1103.huesped.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor

public class HuespedDTO {

private Long id;
private String nombre;
private String correo;
private String documentoIdentidad;



}
