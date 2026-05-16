package cl.duocuc.dsy1103.reserva.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient; 
import cl.duocuc.dsy1103.reserva.dto.ReservaRequest;
import cl.duocuc.dsy1103.reserva.dto.ReservaResponse;
import cl.duocuc.dsy1103.reserva.mapper.ReservaMapper;
import cl.duocuc.dsy1103.reserva.model.Reserva;
import cl.duocuc.dsy1103.reserva.repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private ReservaMapper mapper;

    @Autowired
    @Qualifier("authRestClient")
    private RestClient authRestClient;

    @Autowired
    @Qualifier("hotelRestClient")
    private RestClient hotelRestClient;

    public List<ReservaResponse> obtenerTodasLasReservas() {
        log.info("Obteniendo la lista de todas las reservas");
        List<Reserva> reservas = repository.findAll();
        return reservas.stream().map(mapper::toResponse).toList();
    }

    public ReservaResponse obtenerReservaPorId(Long id) {
        log.info("Buscando reserva con id: {}", id);
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con el id: " + id));
        return mapper.toResponse(reserva);
    }

    public ReservaResponse crearReserva(ReservaRequest request) {
        log.info("Creando una nueva reserva. Validando mediante RestClient...");
        
        
        authRestClient.get()
                .uri("/api/auth/{id}", request.getIdUsuario())
                .retrieve()
                .toBodilessEntity(); // Levanta excepción automática si el ID no existe (404/500)

        hotelRestClient.get()
                .uri("/api/hoteles/{id}", request.getIdHabitacion())
                .retrieve()
                .toBodilessEntity();

        Reserva reserva = repository.save(mapper.toEntity(request));
        return mapper.toResponse(reserva);
    }

    public ReservaResponse actualizarReserva(Long id, ReservaRequest request) {
        log.info("Actualizando reserva con id: {}", id);
        Reserva existingReserva = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con el id: " + id));

        existingReserva.setIdUsuario(request.getIdUsuario());
        existingReserva.setIdHabitacion(request.getIdHabitacion());
        existingReserva.setFechaInicio(request.getFechaInicio());
        existingReserva.setFechaFin(request.getFechaFin());

        Reserva updatedReserva = repository.save(existingReserva);
        return mapper.toResponse(updatedReserva);
    }

    public void eliminarReserva(Long id) {
        log.info("Eliminando reserva con id: {}", id);
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Reserva no encontrada con el id: " + id);
        }
        repository.deleteById(id);
    }
}