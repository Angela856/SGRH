package cl.duocuc.dsy1103.servicioextra.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ServicioExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descripcion;
    private Double precio;
}