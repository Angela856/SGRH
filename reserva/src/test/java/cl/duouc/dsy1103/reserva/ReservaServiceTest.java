package cl.duouc.dsy1103.reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duocuc.dsy1103.reserva.client.AuthClient;
import cl.duocuc.dsy1103.reserva.client.HotelClient;
import cl.duocuc.dsy1103.reserva.dto.ReservaRequest;
import cl.duocuc.dsy1103.reserva.dto.ReservaResponse;
import cl.duocuc.dsy1103.reserva.mapper.ReservaMapper;
import cl.duocuc.dsy1103.reserva.model.Reserva;
import cl.duocuc.dsy1103.reserva.repository.ReservaRepository;
import cl.duocuc.dsy1103.reserva.service.ReservaService;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository repository;

    @Mock
    private ReservaMapper mapper;

    @Mock
    private AuthClient authClient;   // Mock de tu nuevo cliente de Autorización

    @Mock
    private HotelClient hotelClient; // Mock de tu nuevo cliente de Hotel

    @InjectMocks
    private ReservaService reservaService;

    // ==========================================
    // 1. TEST: OBTENER TODAS LAS RESERVAS
    // ==========================================
    @Test
    @DisplayName("Debería retornar una lista con todas las reservas registradas")
    void deberiaObtenerTodasLasReservas() {
        // GIVEN
        Reserva r1 = new Reserva();
        Reserva r2 = new Reserva();
        when(repository.findAll()).thenReturn(List.of(r1, r2));
        when(mapper.toResponse(any(Reserva.class))).thenReturn(new ReservaResponse());

        // WHEN
        List<ReservaResponse> resultado = reservaService.obtenerTodasLasReservas();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    // ==========================================
    // 2. TEST: OBTENER RESERVA POR ID (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería obtener una reserva específica por su ID")
    void deberiaObtenerReservaPorIdExitosamente() {
        // GIVEN
        Long idBusqueda = 1L;
        Reserva reservaMock = new Reserva();
        reservaMock.setId(idBusqueda);

        ReservaResponse responseMock = new ReservaResponse();
        responseMock.setId(idBusqueda);
        responseMock.setIdUsuario(10L);
        responseMock.setIdHabitacion(50L);

        when(repository.findById(idBusqueda)).thenReturn(Optional.of(reservaMock));
        when(mapper.toResponse(reservaMock)).thenReturn(responseMock);

        // WHEN
        ReservaResponse resultado = reservaService.obtenerReservaPorId(idBusqueda);

        // THEN
        assertNotNull(resultado);
        assertEquals(idBusqueda, resultado.getId());
        assertEquals(10L, resultado.getIdUsuario());
        verify(repository, times(1)).findById(idBusqueda);
    }

    // ==========================================
    // 3. TEST: OBTENER RESERVA POR ID (EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar NoSuchElementException cuando el ID de reserva no existe")
    void deberiaLanzarExcepcionCuandoReservaNoExiste() {
        // GIVEN
        Long idInexistente = 99L;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            reservaService.obtenerReservaPorId(idInexistente);
        });
        verify(repository, times(1)).findById(idInexistente);
    }

    // ==========================================
    // 4. TEST: CREAR RESERVA (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería crear una reserva tras validar exitosamente usuario y hotel externos")
    void deberiaCrearReservaExitosamente() {
        // GIVEN
        ReservaRequest request = new ReservaRequest();
        request.setIdUsuario(10L);
        request.setIdHabitacion(50L);

        // Simulamos que los clientes de red responden con éxito (sin lanzar excepciones)
        when(authClient.obtenerUsuarioPorId(request.getIdUsuario())).thenReturn(new Object());
        doNothing().when(hotelClient).validarExistenciaHotel(request.getIdHabitacion());

        Reserva reservaEntidad = new Reserva();
        when(mapper.toEntity(any(ReservaRequest.class))).thenReturn(reservaEntidad);
        when(repository.save(any(Reserva.class))).thenReturn(reservaEntidad);

        ReservaResponse responseMock = new ReservaResponse();
        responseMock.setId(1L);
        responseMock.setIdUsuario(10L);
        responseMock.setIdHabitacion(50L);
        when(mapper.toResponse(any(Reserva.class))).thenReturn(responseMock);

        // WHEN
        ReservaResponse resultado = reservaService.crearReserva(request);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(authClient, times(1)).obtenerUsuarioPorId(request.getIdUsuario());
        verify(hotelClient, times(1)).validarExistenciaHotel(request.getIdHabitacion());
        verify(repository, times(1)).save(any(Reserva.class));
    }

    // ==========================================
    // 5. TEST: ACTUALIZAR RESERVA
    // ==========================================
    @Test
    @DisplayName("Debería actualizar los campos de una reserva existente")
    void deberiaActualizarReservaExitosamente() {
        // GIVEN
        Long idExistente = 1L;
        ReservaRequest requestUpdate = new ReservaRequest();
        requestUpdate.setIdUsuario(12L);
        requestUpdate.setIdHabitacion(55L);

        Reserva reservaAntigua = new Reserva();
        reservaAntigua.setId(idExistente);

        Reserva reservaActualizada = new Reserva();
        reservaActualizada.setId(idExistente);

        ReservaResponse responseMock = new ReservaResponse();
        responseMock.setId(idExistente);
        responseMock.setIdUsuario(12L);
        responseMock.setIdHabitacion(55L);

        when(repository.findById(idExistente)).thenReturn(Optional.of(reservaAntigua));
        when(repository.save(any(Reserva.class))).thenReturn(reservaActualizada);
        when(mapper.toResponse(any(Reserva.class))).thenReturn(responseMock);

        // WHEN
        ReservaResponse resultado = reservaService.actualizarReserva(idExistente, requestUpdate);

        // THEN
        assertNotNull(resultado);
        assertEquals(12L, resultado.getIdUsuario());
        assertEquals(55L, resultado.getIdHabitacion());
        verify(repository, times(1)).findById(idExistente);
        verify(repository, times(1)).save(any(Reserva.class));
    }

    // ==========================================
    // 6. TEST: ELIMINAR RESERVA
    // ==========================================
    @Test
    @DisplayName("Debería eliminar una reserva de la BD si el ID ingresado es válido")
    void deberiaEliminarReservaExitosamente() {
        // GIVEN
        Long idExistente = 1L;
        when(repository.existsById(idExistente)).thenReturn(true);

        // WHEN
        reservaService.eliminarReserva(idExistente);

        // THEN
        verify(repository, times(1)).deleteById(idExistente);
    }
}