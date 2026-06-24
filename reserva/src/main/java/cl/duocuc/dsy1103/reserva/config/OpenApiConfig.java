package cl.duocuc.dsy1103.reserva.config;

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
                        .title("API de Reservas")
                        .version("1.0.0")
                        .description("Orquestador central del ecosistema. Se encarga de coordinar la creación, modificación y estados "
                                   + "de reservas hoteleras consumiendo síncronamente datos de usuarios (8081) y hoteles (8083).")
                        .contact(new Contact()
                                .name("Módulo Operativo - SGRH")
                                .email("cliente@duocuc.cl")));
    }
}
