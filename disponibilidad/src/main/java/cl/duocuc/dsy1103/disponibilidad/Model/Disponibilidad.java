package cl.duocuc.dsy1103.disponibilidad.Model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "disponibilidad")
@Data

public class Disponibilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long habitacionId;
    private boolean disponible;

}
