package cl.duocuc.dsy1103.pago.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "pagos")
@Data
@Builder 
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idReserva;     
    
    private Double monto;
    
    private String metodo;       // DEBITO, CREDITO, TRANSFERENCIA
    
    private LocalDateTime fechaPago;
}