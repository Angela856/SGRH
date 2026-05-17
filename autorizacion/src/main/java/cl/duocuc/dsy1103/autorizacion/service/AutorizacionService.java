package cl.duocuc.dsy1103.autorizacion.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duocuc.dsy1103.autorizacion.dto.AuthRequest;
import cl.duocuc.dsy1103.autorizacion.dto.AuthResponse;
import cl.duocuc.dsy1103.autorizacion.mapper.AuthMapper;
import cl.duocuc.dsy1103.autorizacion.model.Usuario;
import cl.duocuc.dsy1103.autorizacion.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AutorizacionService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthMapper mapper;

    public List<AuthResponse> obtenerTodosLosUsuarios() {
        log.info("Obteniendo la lista de todos los usuarios");
        List<Usuario> usuarios = repository.findAll();
        return usuarios.stream().map(mapper::toResponse).toList();
    }

    public AuthResponse obtenerUsuarioPorId(Long id) {
        log.info("Buscando usuario con id: {}", id);
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con el id: " + id));
        return mapper.toResponse(usuario);
    }

    public AuthResponse crearUsuario(AuthRequest request) {
        // CORREGIDO: Se cambia request.getEmail() por request.getCorreo()
        log.info("Creando un nuevo usuario: {}", request.getCorreo());
        Usuario usuario = repository.save(mapper.toEntity(request));
        return mapper.toResponse(usuario);
    }

    public AuthResponse actualizarUsuario(Long id, AuthRequest request) {
        log.info("Actualizando usuario con id: {}", id);
        Usuario existingUser = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con el id: " + id));

        existingUser.setCorreo(request.getCorreo());
        existingUser.setContrasena(request.getContrasena());
        existingUser.setRol(request.getRol());

        Usuario updatedUser = repository.save(existingUser);
        return mapper.toResponse(updatedUser);
    }

    public void eliminarUsuario(Long id) {
        log.info("Eliminando usuario con id: {}", id);
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Usuario no encontrado con el id: " + id);
        }
        repository.deleteById(id);
    }

    // Método necesario para dar soporte al controlador que tienes implementado
    public boolean login(String correo, String contrasena) {
        log.info("Intentando iniciar sesión para el correo: {}", correo);
        return repository.findByCorreo(correo)
                .map(usuario -> usuario.getContrasena().equals(contrasena))
                .orElse(false);
    }
}