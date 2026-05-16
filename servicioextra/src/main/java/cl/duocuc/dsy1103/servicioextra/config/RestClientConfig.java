package cl.duocuc.dsy1103.servicioextra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${servicios.reserva.url}")
    private String reservaServiceUrl;

    @Bean(name = "reservaRestClient")
    public RestClient reservaRestClient() {
        return RestClient.builder()
                .baseUrl(reservaServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}