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

    @Column(name = "habitacion_id", nullable = false)
    private Long habitacionId;

    @Column(name = "huesped_id", nullable = false)
    private Long huespedId;


    @Column(name = "texto", nullable = false, length = 500)
    private String texto;

    
    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;
}
