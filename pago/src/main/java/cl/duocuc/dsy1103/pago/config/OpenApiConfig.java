package cl.duocuc.dsy1103.pago.config; // <- Ajusta según tu paquete real

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
                        .title("API de Procesamiento de Pagos")
                        .version("1.0.0")
                        .description("Este microservicio gestiona las transacciones financieras del hotel. "
                                   + "Se encarga de procesar pagos, registrar comprobantes y verificar el estado de las cuentas "
                                   + "enlazando la información con el sistema de reservas externas.")
                        .contact(new Contact()
                                .name("Módulo Financiero - SGRH")
                                .email("cliente@duocuc.cl")));
    }
}
