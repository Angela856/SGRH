package cl.duocuc.dsy1103.pago.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReservaClient {

    private final RestClient reservaRestClient;

    public ReservaClient(@Qualifier("reservaRestClient") RestClient reservaRestClient) {
        this.reservaRestClient = reservaRestClient;
    }

    public Object obtenerReservaPorId(Long idReserva) {
        try {
            log.info("Client: Consultando existencia de la reserva con ID: {}", idReserva);
            return reservaRestClient.get()
                    .uri("/api/reservas/{id}", idReserva)
                    .retrieve()
                    .body(Object.class); // Mapeo dinámico y genérico (evita errores de importación)
        } catch (Exception e) {
            log.error("Error HTTP al conectar con el microservicio de Reserva para ID {}: {}", idReserva, e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "La reserva con ID '" + idReserva + "' no existe o el sistema no se encuentra disponible.");
        }
    }
}
