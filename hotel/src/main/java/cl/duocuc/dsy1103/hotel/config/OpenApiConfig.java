package cl.duocuc.dsy1103.hotel.config;

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
                        .title("API de Infraestructura y Sucursales Hoteleras")
                        .version("1.0.0")
                        .description("Este microservicio provee el núcleo de operaciones CRUD para la administración integral " 
                                   + "de las sucursales físicas del hotel. Permite registrar nuevos establecimientos, " 
                                   + "actualizar datos de contacto, listar sedes disponibles y dar de baja sucursales del sistema.")
                        .contact(new Contact()
                                .name("Módulo de Gestión Hotelera - SGRH")
                                .email("soporte@duocuc.cl")));
    }
}