package cl.duocuc.dsy1103.autorizacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String correo;
    private String contrasena;
    private String rol; // ADMIN, RECEPCIONISTA, CLIENTE
}