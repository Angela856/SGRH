package cl.duocuc.dsy1103.servicioextra.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servicios_extras")
@Data
@Builder 
@AllArgsConstructor
@NoArgsConstructor
public class ServicioExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idReserva;      
    
    private String descripcion;  
    
    private Double precio;
}