package cl.duocuc.dsy1103.servicioextra.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "servicios_extras")
@Data
public class ServicioExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private String descripcion;
}