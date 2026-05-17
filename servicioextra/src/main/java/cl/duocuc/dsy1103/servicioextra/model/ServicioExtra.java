package cl.duocuc.dsy1103.servicioextra.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servicios_extras")
@Data
@Builder // Habilita el uso de .builder() en ServicioExtraMapper
@AllArgsConstructor
@NoArgsConstructor
public class ServicioExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idReserva;      // Valida la existencia contra Reserva por WebClient
    
    private String descripcion;  
    
    private Double precio;
}