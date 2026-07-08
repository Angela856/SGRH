package cl.duocuc.dsy1103.pago;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duocuc.dsy1103.pago.client.ReservaClient;
import cl.duocuc.dsy1103.pago.dto.PagoRequest;
import cl.duocuc.dsy1103.pago.dto.PagoResponse;
import cl.duocuc.dsy1103.pago.mapper.PagoMapper;
import cl.duocuc.dsy1103.pago.model.Pago;
import cl.duocuc.dsy1103.pago.repository.PagoRepository;
import cl.duocuc.dsy1103.pago.service.PagoService;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private PagoMapper mapper;

    @Mock
    private ReservaClient reservaClient; // Mockeamos directamente tu nuevo cliente desacoplado

    @InjectMocks
    private PagoService pagoService;

    // ==========================================
    // 1. TEST: OBTENER TODOS LOS PAGOS
    // ==========================================
    @Test
    @DisplayName("Debería retornar una lista con todos los pagos registrados")
    void deberiaObtenerTodosLosPagos() {
        // GIVEN
        Pago p1 = new Pago();
        Pago p2 = new Pago();
        when(repository.findAll()).thenReturn(List.of(p1, p2));
        when(mapper.toResponse(any(Pago.class))).thenReturn(new PagoResponse());

        // WHEN
        List<PagoResponse> resultado = pagoService.obtenerTodosLosPagos();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    // ==========================================
    // 2. TEST: OBTENER POR ID (CASO ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería obtener un registro de pago específico por su ID")
    void deberiaObtenerPagoPorIdExitosamente() {
        // GIVEN
        Long idBusqueda = 105L;
        Pago pagoMock = new Pago();
        pagoMock.setId(idBusqueda);

        // Usamos el @Builder real de tu PagoResponse
        PagoResponse responseMock = PagoResponse.builder()
                .id(idBusqueda)
                .idReserva(1L)
                .monto(65000.50)
                .metodo("TRANSBANK")
                .estado("APROBADO")
                .fechaPago(LocalDateTime.now())
                .build();

        when(repository.findById(idBusqueda)).thenReturn(Optional.of(pagoMock));
        when(mapper.toResponse(pagoMock)).thenReturn(responseMock);

        // WHEN
        PagoResponse resultado = pagoService.obtenerPagoPorId(idBusqueda);

        // THEN
        assertNotNull(resultado);
        assertEquals(idBusqueda, resultado.getId());
        assertEquals("APROBADO", resultado.getEstado());
        verify(repository, times(1)).findById(idBusqueda);
    }

    // ==========================================
    // 3. TEST: OBTENER POR ID (CASO EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar NoSuchElementException cuando el ID de pago no existe")
    void deberiaLanzarExcepcionCuandoPagoNoExiste() {
        // GIVEN
        Long idInexistente = 999L;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            pagoService.obtenerPagoPorId(idInexistente);
        });
        verify(repository, times(1)).findById(idInexistente);
    }

    // ==========================================
    // 4. TEST: CREAR PAGO (CASO ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería registrar un pago de forma exitosa tras validar la reserva externa")
    void deberiaCrearPagoExitosamente() {
        // GIVEN
        PagoRequest request = new PagoRequest();
        request.setIdReserva(1L);
        request.setMonto(65000.50);
        request.setMetodo("TRANSBANK");

        // Simulamos que el cliente de reservas encuentra la reserva sin problemas (retorna cualquier objeto)
        when(reservaClient.obtenerReservaPorId(request.getIdReserva())).thenReturn(new Object());

        Pago pagoEntidad = new Pago();
        when(mapper.toEntity(any(PagoRequest.class))).thenReturn(pagoEntidad);
        when(repository.save(any(Pago.class))).thenReturn(pagoEntidad);

        PagoResponse responseMock = PagoResponse.builder()
                .id(105L)
                .idReserva(request.getIdReserva())
                .monto(request.getMonto())
                .estado("APROBADO")
                .build();
        when(mapper.toResponse(any(Pago.class))).thenReturn(responseMock);

        // WHEN
        PagoResponse resultado = pagoService.crearPago(request);

        // THEN
        assertNotNull(resultado);
        assertEquals(105L, resultado.getId());
        assertEquals("APROBADO", resultado.getEstado());
        verify(reservaClient, times(1)).obtenerReservaPorId(request.getIdReserva());
        verify(repository, times(1)).save(any(Pago.class));
    }

    // ==========================================
    // 5. TEST: ACTUALIZAR PAGO
    // ==========================================
    @Test
    @DisplayName("Debería actualizar los datos de un registro de pago existente")
    void deberiaActualizarPagoExitosamente() {
        // GIVEN
        Long idExistente = 105L;
        PagoRequest requestUpdate = new PagoRequest();
        requestUpdate.setIdReserva(2L);
        requestUpdate.setMonto(70000.00);
        requestUpdate.setMetodo("EFECTIVO");

        Pago pagoAntiguo = new Pago();
        pagoAntiguo.setId(idExistente);

        Pago pagoActualizado = new Pago();
        pagoActualizado.setId(idExistente);

        PagoResponse responseMock = PagoResponse.builder()
                .id(idExistente)
                .idReserva(2L)
                .monto(70000.00)
                .metodo("EFECTIVO")
                .build();

        when(repository.findById(idExistente)).thenReturn(Optional.of(pagoAntiguo));
        when(repository.save(any(Pago.class))).thenReturn(pagoActualizado);
        when(mapper.toResponse(any(Pago.class))).thenReturn(responseMock);

        // WHEN
        PagoResponse resultado = pagoService.actualizarPago(idExistente, requestUpdate);

        // THEN
        assertNotNull(resultado);
        assertEquals(70000.00, resultado.getMonto());
        verify(repository, times(1)).findById(idExistente);
        verify(repository, times(1)).save(any(Pago.class));
    }

    // ==========================================
    // 6. TEST: ELIMINAR PAGO
    // ==========================================
    @Test
    @DisplayName("Debería eliminar un registro de pago si el ID es válido")
    void deberiaEliminarPagoExitosamente() {
        // GIVEN
        Long idExistente = 105L;
        when(repository.existsById(idExistente)).thenReturn(true);

        // WHEN
        pagoService.eliminarPago(idExistente);

        // THEN
        verify(repository, times(1)).deleteById(idExistente);
    }
}
