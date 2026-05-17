package cl.duocuc.dsy1103.hotel.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hoteles")
@Data
@Builder // Habilita el uso de .builder() en HotelMapper
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    private String direccion;
    
    private String estrellas; 
}
