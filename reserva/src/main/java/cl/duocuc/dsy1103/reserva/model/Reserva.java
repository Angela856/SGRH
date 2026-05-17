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
    
    private Long idUsuario;      
    
    private Long idHabitacion;   
    
    private LocalDate fechaInicio;
    
    private LocalDate fechaFin;
    
    private String estado;       // PENDIENTE, CONFIRMADO, CANCELADO
}