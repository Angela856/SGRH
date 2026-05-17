package cl.duocuc.dsy1103.servicioextra.mapper;

import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraRequest;
import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraResponse;
import cl.duocuc.dsy1103.servicioextra.model.ServicioExtra;
import org.springframework.stereotype.Component;

@Component
public class ServicioExtraMapper {

    public ServicioExtra toEntity(ServicioExtraRequest request) {
        if (request == null) {
            return null;
        }
        ServicioExtra servicio = new ServicioExtra();
        servicio.setDescripcion(request.getDescripcion());
        servicio.setPrecio(request.getPrecio());
        return servicio;
    }

    public ServicioExtraResponse toResponse(ServicioExtra entity) {
        if (entity == null) {
            return null;
        }
        // Construye la respuesta esperada por el controlador
        return ServicioExtraResponse.builder()
                .id(entity.getId())
                .descripcion(entity.getDescripcion())
                .precio(entity.getPrecio())
                .build();
    }
}
