package cl.duocuc.dsy1103.reserva.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long habitacionId; 
    private Long usuarioId;    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado; 
}