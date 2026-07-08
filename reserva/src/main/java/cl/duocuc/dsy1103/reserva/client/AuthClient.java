package cl.duocuc.dsy1103.reserva.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthClient {

    private final RestClient authRestClient;

    public AuthClient(@Qualifier("authRestClient") RestClient authRestClient) {
        this.authRestClient = authRestClient;
    }

    // Cambiamos AuthResponse por Object para no necesitar el import externo
    public Object obtenerUsuarioPorId(Long idUsuario) {
        try {
            log.info("Client: Consultando datos del usuario con ID: {}", idUsuario);
            return authRestClient.get()
                    .uri("/api/auth/{id}", idUsuario)
                    .retrieve()
                    .body(Object.class); // Spring lo transforma automáticamente en un mapa genérico
        } catch (Exception e) {
            log.error("Error HTTP al conectar con Autorización para ID {}: {}", idUsuario, e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "El usuario con ID '" + idUsuario + "' no existe en los registros de autenticación.");
        }
    }
}