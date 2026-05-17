package cl.duocuc.dsy1103.tarifa.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarifas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tarifa {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private Long habitacionId;
    private double precioNoche;
    private String temporada;

}
