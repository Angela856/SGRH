package cl.duocuc.dsy1103.reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import cl.duocuc.dsy1103.reserva.dto.ReservaRequest;
import cl.duocuc.dsy1103.reserva.dto.ReservaResponse;
import cl.duocuc.dsy1103.reserva.mapper.ReservaMapper;
import cl.duocuc.dsy1103.reserva.model.Reserva;
import cl.duocuc.dsy1103.reserva.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository repository;

    @Mock
    private ReservaMapper mapper;

    // Evitamos problemas de genéricos usando Deep Stubs para ambos clientes HTTP externos
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient authRestClient;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient hotelRestClient;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Debería crear una reserva exitosamente validando usuario y habitación externos")
    void deberiaCrearReservaExitosamente() {
        
        // GIVEN (Preparación del escenario usando tus DTOs reales)
        ReservaRequest request = new ReservaRequest();
        request.setIdUsuario(12L);
        request.setIdHabitacion(105L);
        request.setFechaInicio(LocalDate.of(2026, 7, 1));
        request.setFechaFin(LocalDate.of(2026, 7, 5));

        // 1. Mockear las dos llamadas HTTP fluidas en una sola línea gracias a Deep Stubs
        when(authRestClient.get().uri(any(String.class), any(Object.class)).retrieve().toBodilessEntity())
                .thenReturn(ResponseEntity.ok().build());

        when(hotelRestClient.get().uri(any(String.class), any(Object.class)).retrieve().toBodilessEntity())
                .thenReturn(ResponseEntity.ok().build());

        // 2. Mockear el mapeo inicial (Request -> Entity)
        Reserva entidadSimulada = new Reserva();
        entidadSimulada.setIdUsuario(12L);
        entidadSimulada.setIdHabitacion(105L);
        when(mapper.toEntity(any(ReservaRequest.class))).thenReturn(entidadSimulada);

        // 3. Mockear la persistencia en base de datos
        Reserva entidadGuardada = new Reserva();
        entidadGuardada.setId(1L);
        entidadGuardada.setIdUsuario(12L);
        entidadGuardada.setIdHabitacion(105L);
        when(repository.save(any(Reserva.class))).thenReturn(entidadGuardada);

        // 4. Mockear el mapeo de salida usando el patrón @Builder real de tu ReservaResponse
        ReservaResponse responseSimulado = ReservaResponse.builder()
                .id(1L)
                .idUsuario(12L)
                .idHabitacion(105L)
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .estado("CONFIRMADA")
                .createdAt(LocalDateTime.now())
                .build();
                
        when(mapper.toResponse(any(Reserva.class))).thenReturn(responseSimulado);

        // WHEN (Ejecución del método real)
        ReservaResponse response = reservaService.crearReserva(request);

        // THEN (Aserciones y validaciones)
        assertNotNull(response, "La respuesta de la reserva no debería ser nula");
        assertEquals(1L, response.getId(), "El ID retornado debería ser 1");
        assertEquals(12L, response.getIdUsuario(), "El ID de usuario no coincide");
        assertEquals(105L, response.getIdHabitacion(), "El ID de la habitación no coincide");
        assertEquals("CONFIRMADA", response.getEstado(), "El estado de la reserva debería ser CONFIRMADA");

        // Verificaciones de comportamiento de los componentes mockeados
        verify(authRestClient, times(1)).get();
        verify(hotelRestClient, times(1)).get();
        verify(repository, times(1)).save(any(Reserva.class));
        verify(mapper, times(1)).toResponse(any(Reserva.class));
    }

    @Test
    @DisplayName("Debería eliminar una reserva de forma exitosa si existe el ID")
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