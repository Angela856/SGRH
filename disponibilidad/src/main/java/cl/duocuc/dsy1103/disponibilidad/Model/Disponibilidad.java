package cl.duocuc.dsy1103.disponibilidad.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "disponibilidad")
@Data
@AllArgsConstructor 
@NoArgsConstructor  
public class Disponibilidad {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "habitacion_id", nullable = false)
    private Long habitacionId;

    
    @Column(name = "disponible", nullable = false)
    private Boolean disponible;
}