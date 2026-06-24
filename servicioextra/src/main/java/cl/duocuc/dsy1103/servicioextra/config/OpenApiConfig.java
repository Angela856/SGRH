package cl.duocuc.dsy1103.servicioextra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Servicios Adicionales")
                        .version("1.0.0")
                        .description("Este microservicio gestiona los cargos por servicios extras contratados por los huéspedes "
                                   + "(Spa, alimentación, tours, etc.). Los consumos se vinculan directamente a un código de reserva activo.")
                        .contact(new Contact()
                                .name("Módulo de Experiencia del Huésped - SGRH")
                                .email("cliente@duocuc.cl")));
    }
}