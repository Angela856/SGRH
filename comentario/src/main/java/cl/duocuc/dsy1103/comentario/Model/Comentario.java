package cl.duocuc.dsy1103.comentario.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comentarios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Comentario {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private Long habitacionId;
private Long huespedId;
private String texto;
private int calificacion;
}



