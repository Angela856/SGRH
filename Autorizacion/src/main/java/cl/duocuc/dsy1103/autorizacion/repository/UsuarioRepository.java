package cl.duocuc.dsy1103.autorizacion.repository;

import cl.duocuc.dsy1103.autorizacion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método extra para buscar por email al hacer login
    Optional<Usuario> findByCorreo(String correo);
}