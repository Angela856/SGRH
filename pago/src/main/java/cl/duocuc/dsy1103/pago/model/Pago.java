package cl.duocuc.dsy1103.pago.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idReserva;
    
    @Positive
    private Double monto;
    
    private String metodo; // TRANSBANK, PAYPAL
    private LocalDateTime fechaPago;
}