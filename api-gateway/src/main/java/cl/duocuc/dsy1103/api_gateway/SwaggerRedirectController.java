package cl.duocuc.dsy1103.api_gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class SwaggerRedirectController {

    @GetMapping("/swagger-ui.html")
    public Mono<Void> redirect(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        response.getHeaders().setLocation(URI.create("/webjars/swagger-ui/index.html"));
        return response.setComplete();
    }
}