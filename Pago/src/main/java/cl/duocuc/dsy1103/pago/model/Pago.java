package cl.duocuc.dsy1103.pago.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reservaId;
    private Double montoTotal;
    private String metodoPago;
    private LocalDateTime fechaPago;
}