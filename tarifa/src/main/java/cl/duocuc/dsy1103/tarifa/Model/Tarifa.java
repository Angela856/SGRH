package cl.duocuc.dsy1103.tarifa.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarifas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tarifa {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "habitacion_id", nullable = false)
    private Long habitacionId;

   
    @Column(name = "precio_noche", nullable = false)
    private Double precioNoche;

    // CORRECCIÓN TÉCNICA: Definimos el largo máximo para optimizar el espacio en la base de datos de Laragon
    @Column(name = "temporada", nullable = false, length = 20)
    private String temporada;
}
