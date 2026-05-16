package cl.duocuc.dsy1103.reserva.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${servicios.autorizacion.url}")
    private String autorizacionServiceUrl;

    @Value("${servicios.hotel.url}")
    private String hotelServiceUrl;

    @Bean(name = "authRestClient")
    public RestClient authRestClient() {
        return RestClient.builder()
                .baseUrl(autorizacionServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean(name = "hotelRestClient")
    public RestClient hotelRestClient() {
        return RestClient.builder()
                .baseUrl(hotelServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}