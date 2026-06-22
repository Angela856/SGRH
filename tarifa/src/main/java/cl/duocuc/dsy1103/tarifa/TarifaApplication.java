package cl.duocuc.dsy1103.tarifa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// CORRECCIÓN DUOC: Habilita el escaneo de clientes remotos Feign para comunicarse con otros microservicios
@EnableFeignClients 
// CUMPLIMIENTO SWAGGER: Define el título, descripción y versión de la API de forma centralizada
@OpenAPIDefinition(
    info = @Info(
        title = "Microservicio de Tarifas - SGRH",
        version = "1.0.0",
        description = "API para la administración de precios, temporadas y cotizaciones de habitaciones en el Sistema de Gestión de Recursos Hoteleros.",
        contact = @Contact(name = "Módulo de Almendra", email = "almendra@duocuc.cl")
    )
)
public class TarifaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarifaApplication.class, args);
	}

}
