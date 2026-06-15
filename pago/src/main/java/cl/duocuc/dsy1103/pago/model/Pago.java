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

    @Column(name= "id_reserva", nullable = false)
    private Long idReserva;     

    @Column(name= "monto", nullable= false)
    private Double monto;

    @Column(name= "metodo", nullable= false)
    private String metodo;       // DEBITO, CREDITO, TRANSFERENCIA

    @Column(name= "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;
}
