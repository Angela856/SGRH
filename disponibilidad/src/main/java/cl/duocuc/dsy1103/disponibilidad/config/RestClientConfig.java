package cl.duocuc.dsy1103.disponibilidad.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration

public class RestClientConfig {
@Value("${servicios.habitacion.url:http://localhost:8084/api/habitacion}")
    private String habitacionServiceUrl;

    @Bean(name = "habitacionRestClient")
    public RestClient habitacionRestClient() {
        return RestClient.builder()
                .baseUrl(habitacionServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}


