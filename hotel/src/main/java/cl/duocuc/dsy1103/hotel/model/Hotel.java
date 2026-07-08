package cl.duocuc.dsy1103.hotel.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hoteles")
@Data
@Builder 
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Este actuará como el "idHabitacion" que busca el servicio de reservas
    
    private String nombre; // Ej: "Hotel Santiago - Sucursal Centro"
    
    private String direccion;
    
    private String estrellas; 

    private String numeroHabitacion; // Ej: "Suite 402", "Habitación 105"
    
    private Double precioPorNoche; // Necesario para que el micro de Pago cobre con sentido lógico
    
    private Boolean disponible; // Para saber si se puede reservar o ya está ocupada
}