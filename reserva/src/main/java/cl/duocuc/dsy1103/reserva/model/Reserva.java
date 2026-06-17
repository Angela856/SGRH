package cl.duocuc.dsy1103.reserva.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "reservas")
@Data
@Builder 
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name= "id_usuario", nullable = false)
    private Long idUsuario;      
    
    @Column(name= "id_habitacion", nullable = false)
    private Long idHabitacion;   
    
    @Column(name= "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    
    @Column(name= "fecha_fin", nullable = false)
    private LocalDate fechaFin;
    
    private String estado;       // PENDIENTE, CONFIRMADO, CANCELADO
}