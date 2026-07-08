package cl.duocuc.dsy1103.disponibilidad.Client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component

public class HabitacionClient {
    private final RestClient restClient;


    public HabitacionClient(@Qualifier("habitacionRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public Boolean verificarHabitacionExiste(Long id) {
        try {
            return restClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .body(Boolean.class);
        } catch (Exception e) {
            // Si el servicio de habitaciones está abajo o da error, retornamos false de forma segura
            return false;
        }
    }
}


