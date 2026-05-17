package cl.duocuc.dsy1103.huesped.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "huespedes")
@Data

public class Huesped {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@NotBlank(message = "El nombre completo es obligatorio")
private String nombre;

@Email(message = "Debe ingresar un correo electrónico válido")
@NotBlank(message = "El correo es obligatorio")
@Column(unique = true)
private String correo;

@NotBlank(message = "El documento de identidad (RUT/Pasaporte) es obligatorio")
private String documentoIdentidad;


}
