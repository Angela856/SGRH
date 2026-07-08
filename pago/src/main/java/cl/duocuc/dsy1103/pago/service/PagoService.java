package cl.duocuc.dsy1103.pago.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

import cl.duocuc.dsy1103.pago.client.ReservaClient;
import cl.duocuc.dsy1103.pago.dto.PagoRequest;
import cl.duocuc.dsy1103.pago.dto.PagoResponse;
import cl.duocuc.dsy1103.pago.mapper.PagoMapper;
import cl.duocuc.dsy1103.pago.model.Pago;
import cl.duocuc.dsy1103.pago.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PagoService {

    private final PagoRepository repository;
    private final PagoMapper mapper;
    private final ReservaClient reservaClient;

    // El constructor ahora recibe de forma limpia nuestro ReservaClient
    public PagoService(PagoRepository repository, PagoMapper mapper, ReservaClient reservaClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.reservaClient = reservaClient;
    }

    public List<PagoResponse> obtenerTodosLosPagos() {
        log.info("Obteniendo la lista de todos los pagos");
        List<Pago> pagos = repository.findAll();
        return pagos.stream().map(mapper::toResponse).toList();
    }

    public PagoResponse obtenerPagoPorId(Long id) {
        log.info("Buscando pago con id: {}", id);
        Pago pago = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro de pago no encontrado con el id: " + id));
        return mapper.toResponse(pago);
    }

    public PagoResponse crearPago(PagoRequest request) {
        log.info("Registrando pago. Verificando reserva mediante cliente dedicado...");
        
        // Delegamos de forma limpia la llamada de red a la capa Client con try-catch integrado
        reservaClient.obtenerReservaPorId(request.getIdReserva());

        Pago pago = repository.save(mapper.toEntity(request));
        return mapper.toResponse(pago);
    }

    public PagoResponse actualizarPago(Long id, PagoRequest request) {
        log.info("Actualizando registro de pago con id: {}", id);
        Pago existingPago = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro de pago no encontrado con el id: " + id));
        
        existingPago.setIdReserva(request.getIdReserva());
        existingPago.setMonto(request.getMonto());
        existingPago.setMetodo(request.getMetodo());

        Pago updatedPago = repository.save(existingPago);
        return mapper.toResponse(updatedPago);
    }

    public void eliminarPago(Long id) {
        log.info("Eliminando registro de pago con id: {}", id);
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Registro de pago no encontrado con el id: " + id);
        }
        repository.deleteById(id);
    }
}