package cl.duocuc.dsy1103.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "habitaciones")
@Data
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String tipo; 
    private Double precioNoche;
    private Boolean disponible;
}