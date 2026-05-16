package cl.duocuc.dsy1103.servicioextra.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraRequest;
import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraResponse;
import cl.duocuc.dsy1103.servicioextra.mapper.ServicioExtraMapper;
import cl.duocuc.dsy1103.servicioextra.model.ServicioExtra;
import cl.duocuc.dsy1103.servicioextra.repository.ServicioExtraRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServicioExtraService {

    @Autowired
    private ServicioExtraRepository repository;

    @Autowired
    private ServicioExtraMapper mapper;

    @Autowired
    @Qualifier("reservaRestClient")
    private RestClient reservaRestClient;

    public List<ServicioExtraResponse> obtenerTodosLosServicios() {
        log.info("Obteniendo la lista de todos los servicios adicionales");
        List<ServicioExtra> servicios = repository.findAll();
        return servicios.stream().map(mapper::toResponse).toList();
    }

    public ServicioExtraResponse obtenerServicioPorId(Long id) {
        log.info("Buscando servicio adicional con id: {}", id);
        ServicioExtra servicio = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio adicional no encontrado con el id: " + id));
        return mapper.toResponse(servicio);
    }

    public ServicioExtraResponse crearServicioExtra(ServicioExtraRequest request, Long idReserva) {
        log.info("Asociando servicio adicional a la reserva con id: {}", idReserva);
        
        // Validación síncrona moderna con RestClient
        reservaRestClient.get()
                .uri("/api/reservas/{id}", idReserva)
                .retrieve()
                .toBodilessEntity();

        ServicioExtra servicio = mapper.toEntity(request);
        servicio.setIdReserva(idReserva);
        
        ServicioExtra guardado = repository.save(servicio);
        return mapper.toResponse(guardado);
    }

    public ServicioExtraResponse actualizarServicioExtra(Long id, ServicioExtraRequest request) {
        log.info("Actualizando servicio adicional con id: {}", id);
        ServicioExtra existingServicio = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio adicional no encontrado con el id: " + id));

        existingServicio.setDescripcion(request.getDescripcion());
        existingServicio.setPrecio(request.getPrecio());

        ServicioExtra updatedServicio = repository.save(existingServicio);
        return mapper.toResponse(updatedServicio);
    }

    public void eliminarServicioExtra(Long id) {
        log.info("Eliminando servicio adicional con id: {}", id);
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Servicio adicional no encontrado con el id: " + id);
        }
        repository.deleteById(id);
    }
}