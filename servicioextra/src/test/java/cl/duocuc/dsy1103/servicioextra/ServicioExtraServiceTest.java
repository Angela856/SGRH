package cl.duocuc.dsy1103.servicioextra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import cl.duocuc.dsy1103.servicioextra.client.ReservaClient;
import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraRequest;
import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraResponse;
import cl.duocuc.dsy1103.servicioextra.mapper.ServicioExtraMapper;
import cl.duocuc.dsy1103.servicioextra.model.ServicioExtra;
import cl.duocuc.dsy1103.servicioextra.repository.ServicioExtraRepository;
import cl.duocuc.dsy1103.servicioextra.service.ServicioExtraService;

@ExtendWith(MockitoExtension.class)
class ServicioExtraServiceTest {

    @Mock
    private ServicioExtraRepository repository;

    @Mock
    private ServicioExtraMapper mapper;

    @Mock
    private ReservaClient reservaClient; // Mock de nuestro nuevo cliente desacoplado

    @InjectMocks
    private ServicioExtraService servicioExtraService;

    // ==========================================
    // 1. TEST: OBTENER TODOS LOS SERVICIOS
    // ==========================================
    @Test
    @DisplayName("Debería retornar una lista con todos los servicios extras")
    void deberiaObtenerTodosLosServicios() {
        // GIVEN
        ServicioExtra s1 = new ServicioExtra();
        ServicioExtra s2 = new ServicioExtra();
        when(repository.findAll()).thenReturn(List.of(s1, s2));
        when(mapper.toResponse(any(ServicioExtra.class))).thenReturn(new ServicioExtraResponse());

        // WHEN
        List<ServicioExtraResponse> resultado = servicioExtraService.obtenerTodosLosServicios();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    // ==========================================
    // 2. TEST: OBTENER POR ID (CASO ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería obtener un servicio extra específico por su ID")
    void deberiaObtenerServicioPorIdExitosamente() {
        // GIVEN
        Long idBusqueda = 1L;
        ServicioExtra servicioMock = new ServicioExtra();
        servicioMock.setId(idBusqueda);

        ServicioExtraResponse responseMock = ServicioExtraResponse.builder()
                .id(idBusqueda)
                .descripcion("Acceso al Spa y Masaje Relajante")
                .precio(35000.00)
                .idReserva(10L)
                .build();

        when(repository.findById(idBusqueda)).thenReturn(Optional.of(servicioMock));
        when(mapper.toResponse(servicioMock)).thenReturn(responseMock);

        // WHEN
        ServicioExtraResponse resultado = servicioExtraService.obtenerServicioPorId(idBusqueda);

        // THEN
        assertNotNull(resultado);
        assertEquals(idBusqueda, resultado.getId());
        assertEquals("Acceso al Spa y Masaje Relajante", resultado.getDescripcion());
        verify(repository, times(1)).findById(idBusqueda);
    }

    // ==========================================
    // 3. TEST: OBTENER POR ID (CASO EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar NoSuchElementException cuando el ID del servicio extra no existe")
    void deberiaLanzarExcepcionCuandoServicioNoExiste() {
        // GIVEN
        Long idInexistente = 99L;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            servicioExtraService.obtenerServicioPorId(idInexistente);
        });
        verify(repository, times(1)).findById(idInexistente);
    }

    // ==========================================
    // 4. TEST: CREAR SERVICIO EXTRA (CASO ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería asociar y registrar un servicio extra a una reserva válida")
    void deberiaCrearServicioExtraExitosamente() {
        // GIVEN
        Long idReserva = 10L;
        ServicioExtraRequest request = new ServicioExtraRequest("Acceso al Spa", 35000.00);
        ServicioExtra servicioEntidad = new ServicioExtra();

        // Simulamos que el cliente de reservas encuentra la reserva externa con éxito
        when(reservaClient.obtenerReservaPorId(idReserva)).thenReturn(new Object());
        when(mapper.toEntity(request)).thenReturn(servicioEntidad);
        when(repository.save(servicioEntidad)).thenReturn(servicioEntidad);

        ServicioExtraResponse responseMock = ServicioExtraResponse.builder()
                .id(1L)
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .idReserva(idReserva)
                .build();
        when(mapper.toResponse(servicioEntidad)).thenReturn(responseMock);

        // WHEN
        ServicioExtraResponse resultado = servicioExtraService.crearServicioExtra(request, idReserva);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(idReserva, resultado.getIdReserva());
        verify(reservaClient, times(1)).obtenerReservaPorId(idReserva);
        verify(repository, times(1)).save(servicioEntidad);
    }

    // ==========================================
    // 5. TEST: ACTUALIZAR SERVICIO EXTRA
    // ==========================================
    @Test
    @DisplayName("Debería actualizar los datos básicos de un servicio extra existente")
    void deberiaActualizarServicioExtraExitosamente() {
        // GIVEN
        Long idExistente = 1L;
        ServicioExtraRequest requestUpdate = new ServicioExtraRequest("Paseo Guiado Modificado", 40000.00);

        ServicioExtra servicioAntiguo = new ServicioExtra();
        servicioAntiguo.setId(idExistente);

        ServicioExtra servicioActualizado = new ServicioExtra();
        servicioActualizado.setId(idExistente);

        ServicioExtraResponse responseMock = ServicioExtraResponse.builder()
                .id(idExistente)
                .descripcion("Paseo Guiado Modificado")
                .precio(40000.00)
                .build();

        when(repository.findById(idExistente)).thenReturn(Optional.of(servicioAntiguo));
        when(repository.save(any(ServicioExtra.class))).thenReturn(servicioActualizado);
        when(mapper.toResponse(any(ServicioExtra.class))).thenReturn(responseMock);

        // WHEN
        ServicioExtraResponse resultado = servicioExtraService.actualizarServicioExtra(idExistente, requestUpdate);

        // THEN
        assertNotNull(resultado);
        assertEquals("Paseo Guiado Modificado", resultado.getDescripcion());
        assertEquals(40000.00, resultado.getPrecio());
        verify(repository, times(1)).findById(idExistente);
        verify(repository, times(1)).save(any(ServicioExtra.class));
    }

    // ==========================================
    // 6. TEST: ELIMINAR SERVICIO EXTRA
    // ==========================================
    @Test
    @DisplayName("Debería eliminar el registro si el ID ingresado es válido")
    void deberiaEliminarServicioExtraExitosamente() {
        // GIVEN
        Long idExistente = 1L;
        when(repository.existsById(idExistente)).thenReturn(true);

        // WHEN
        servicioExtraService.eliminarServicioExtra(idExistente);

        // THEN
        verify(repository, times(1)).deleteById(idExistente);
    }
}