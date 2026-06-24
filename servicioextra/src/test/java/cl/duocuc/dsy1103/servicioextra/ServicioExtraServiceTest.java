package cl.duocuc.dsy1103.servicioextra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraRequest;
import cl.duocuc.dsy1103.servicioextra.dto.ServicioExtraResponse;
import cl.duocuc.dsy1103.servicioextra.mapper.ServicioExtraMapper;
import cl.duocuc.dsy1103.servicioextra.model.ServicioExtra;
import cl.duocuc.dsy1103.servicioextra.repository.ServicioExtraRepository;
import cl.duocuc.dsy1103.servicioextra.service.ServicioExtraService;

@ExtendWith(MockitoExtension.class)
class ServicioExtraServiceTest {

    @Mock
    private ServicioExtraRepository servicioExtraRepository;

    @Mock
    private ServicioExtraMapper servicioExtraMapper;

    @Mock(answer = org.mockito.Answers.RETURNS_DEEP_STUBS)
    private RestClient reservaRestClient;

    @InjectMocks
    private ServicioExtraService servicioExtraService; // Coincide exactamente con tu clase

    @Test
    @DisplayName("Debería registrar un servicio extra exitosamente cuando los datos son válidos")
    void deberiaRegistrarServicioExtraExitosamente() {
        
        // GIVEN (Preparación del escenario)
        Long idReserva = 1001L;
        ServicioExtraRequest request = new ServicioExtraRequest("Acceso al Spa y Masaje", 35000.0);
        
        // 1. Mockear la llamada encadenada del RestClient para que no de NullPointerException
        when(reservaRestClient.get().uri(any(String.class), any(Object[].class)).retrieve().toBodilessEntity())
            .thenReturn(ResponseEntity.ok().build());

        // 2. Mockear el mapeo inicial (Request -> Entity)
        ServicioExtra entidadSimulada = new ServicioExtra();
        entidadSimulada.setDescripcion(request.getDescripcion());
        entidadSimulada.setPrecio(request.getPrecio());
        when(servicioExtraMapper.toEntity(any(ServicioExtraRequest.class))).thenReturn(entidadSimulada);

        // 3. Mockear la persistencia en base de datos (Repository)
        ServicioExtra entidadGuardada = new ServicioExtra();
        entidadGuardada.setId(1L);
        entidadGuardada.setIdReserva(idReserva);
        entidadGuardada.setDescripcion(request.getDescripcion());
        entidadGuardada.setPrecio(request.getPrecio());
        when(servicioExtraRepository.save(any(ServicioExtra.class))).thenReturn(entidadGuardada);

        // 4. Mockear el mapeo final de salida (Entity -> Response)
        ServicioExtraResponse responseSimulado = new ServicioExtraResponse();
        responseSimulado.setId(1L);
        responseSimulado.setDescripcion("Acceso al Spa y Masaje");
        responseSimulado.setPrecio(35000.0);
        // WHEN (Ejecución del método real)
        when(servicioExtraMapper.toResponse(any(ServicioExtra.class))).thenReturn(responseSimulado);
        
        ServicioExtraResponse response = servicioExtraService.crearServicioExtra(request, idReserva);

        // THEN (Aserciones y validaciones)
        assertNotNull(response, "La respuesta no debería ser nula");
        assertEquals(1L, response.getId(), "El ID retornado debería ser 1");
        assertEquals("Acceso al Spa y Masaje", response.getDescripcion(), "La descripción no coincide");
        assertEquals(35000.0, response.getPrecio(), "El precio registrado no coincide");
        
        // Verificaciones de comportamiento
        verify(servicioExtraRepository, times(1)).save(any(ServicioExtra.class));
        verify(servicioExtraMapper, times(1)).toEntity(request);
        verify(servicioExtraMapper, times(1)).toResponse(entidadGuardada);
    }
}