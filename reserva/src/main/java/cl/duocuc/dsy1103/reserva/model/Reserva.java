package cl.duocuc.dsy1103.reserva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Long idUsuario;
    
    @NotNull
    private Long idHabitacion;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado; // PENDIENTE, CONFIRMADA, CANCELADA
}