package cl.duocuc.dsy1103.reserva.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "reservas")
@Data
@Builder // Habilita el uso de .builder() en ReservaMapper
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idUsuario;      // Conectará síncronamente con Autorizacion por WebClient
    
    private Long idHabitacion;   // Conectará síncronamente con Hotel por WebClient
    
    private LocalDate fechaInicio;
    
    private LocalDate fechaFin;
    
    private String estado;       // PENDIENTE, CONFIRMADA, CANCELADA
}