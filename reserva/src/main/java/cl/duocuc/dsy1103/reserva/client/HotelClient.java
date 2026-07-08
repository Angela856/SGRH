package cl.duocuc.dsy1103.reserva.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HotelClient {

    private final RestClient hotelRestClient;

    public HotelClient(@Qualifier("hotelRestClient") RestClient hotelRestClient) {
        this.hotelRestClient = hotelRestClient;
    }

    public void validarExistenciaHotel(Long idHotel) {
        try {
            log.info("Client: Validando existencia de la sucursal de Hotel ID: {}", idHotel);
            hotelRestClient.get()
                    .uri("/api/hoteles/{id}", idHotel)
                    .retrieve()
                    .toBodilessEntity(); // Solo verifica estados 2xx OK
        } catch (Exception e) {
            log.error("Error HTTP al conectar con el microservicio Hotel para ID {}: {}", idHotel, e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "La sucursal de Hotel con ID '" + idHotel + "' no se encuentra registrada.");
        }
    }
}
