package cl.duocuc.dsy1103.disponibilidad.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "habitacion-service", url = "${servicios.habitacion.url:http://localhost:8084/api/habitacion}")

public interface HabitacionClient {
    @GetMapping("/{id}")
    
    Boolean verificarHabitacionExiste(@PathVariable("id") Long id);
}


