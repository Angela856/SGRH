package cl.duocuc.dsy1103.reserva.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

import cl.duocuc.dsy1103.reserva.client.AuthClient;
import cl.duocuc.dsy1103.reserva.client.HotelClient;
import cl.duocuc.dsy1103.reserva.dto.ReservaRequest;
import cl.duocuc.dsy1103.reserva.dto.ReservaResponse;
import cl.duocuc.dsy1103.reserva.mapper.ReservaMapper;
import cl.duocuc.dsy1103.reserva.model.Reserva;
import cl.duocuc.dsy1103.reserva.repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservaService {

    private final ReservaRepository repository;
    private final ReservaMapper mapper;
    private final AuthClient authClient;
    private final HotelClient hotelClient;
    
    public ReservaService(
            ReservaRepository repository, 
            ReservaMapper mapper, 
            AuthClient authClient, 
            HotelClient hotelClient
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.authClient = authClient;
        this.hotelClient = hotelClient;
    }

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
        log.info("Creando una nueva reserva. Validando mediante infraestructura Client...");
        
        // Llamadas desacopladas con manejo de excepciones integrado
        authClient.obtenerUsuarioPorId(request.getIdUsuario());
        hotelClient.validarExistenciaHotel(request.getIdHabitacion());

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