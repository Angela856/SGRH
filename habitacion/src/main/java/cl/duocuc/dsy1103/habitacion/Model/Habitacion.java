package cl.duocuc.dsy1103.habitacion.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "habitaciones")
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class Habitacion {

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CORRECCIÓN: Trasladamos la validación estética al DTO y dejamos la restricción física de BD aquí
    @Column(name = "numero_habitacion", nullable = false)
    private String numero;

    @Column(name = "hotel_id", nullable = false)
    private Long hotelId;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    // CORRECCIÓN: Ya está con 'Double' mayúscula, lo cual es excelente para evitar líneas amarillas
    @Column(name = "precio_base", nullable = false)
    private Double precioBase;
}