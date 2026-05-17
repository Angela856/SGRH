package cl.duocuc.dsy1103.comentario.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {

private Long id;
private Long habitacionId;
private Long huespedId;
private String texto;
private int calificacion;
}   


