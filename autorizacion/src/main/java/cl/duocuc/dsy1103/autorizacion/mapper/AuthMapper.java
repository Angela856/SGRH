package cl.duocuc.dsy1103.autorizacion.mapper;

import cl.duocuc.dsy1103.autorizacion.dto.AuthRequest;
import cl.duocuc.dsy1103.autorizacion.dto.AuthResponse;
import cl.duocuc.dsy1103.autorizacion.model.Usuario;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class AuthMapper {

    public Usuario toEntity(AuthRequest request) {
        if (request == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(request.getContrasena());
        usuario.setRol(request.getRol());
        return usuario;
    }

    public AuthResponse toResponse(Usuario entity) {
        if (entity == null) {
            return null;
        }
        return AuthResponse.builder()
                .id(entity.getId())
                .correo(entity.getCorreo())
                .rol(entity.getRol())
                .createdAt(LocalDateTime.now()) // Atributo esperado por AuthResponse
                .build();
    }
}