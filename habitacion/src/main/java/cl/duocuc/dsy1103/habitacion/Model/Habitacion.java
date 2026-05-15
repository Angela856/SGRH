package cl.duocuc.dsy1103.habitacion.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "habitaciones")
@Data

public class Habitacion {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@NotBlank(message = "El número de habitación es obligatorio")
@Column(name = "numero_habitacion")
private String numero;

@NotNull(message = "El ID del hotel es obligatorio")
@Column(name = "hotel_id")
private Long hotelId;

@NotBlank(message = "El tipo de habitación es obligatorio (Ej: SIMPLE, DOBLE, SUITE)")
private String tipo;

private Double precioBase;

}
