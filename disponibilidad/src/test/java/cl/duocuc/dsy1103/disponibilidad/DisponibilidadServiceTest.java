package cl.duocuc.dsy1103.disponibilidad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duocuc.dsy1103.disponibilidad.Client.HabitacionClient;
import cl.duocuc.dsy1103.disponibilidad.DTO.DisponibilidadDTO;
import cl.duocuc.dsy1103.disponibilidad.Model.Disponibilidad;
import cl.duocuc.dsy1103.disponibilidad.Repository.DisponibilidadRepository;
import cl.duocuc.dsy1103.disponibilidad.Service.DisponibilidadService;

@ExtendWith(MockitoExtension.class)
class DisponibilidadServiceTest {

    @Mock
    private DisponibilidadRepository repository;

    @Mock
    private HabitacionClient habitacionClient;

    @InjectMocks
    private DisponibilidadService disponibilidadService;

    // ==========================================
    // 1. TEST: GUARDAR DISPONIBILIDAD (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería guardar exitosamente la disponibilidad cuando la habitación existe externamente")
    void deberiaGuardarDisponibilidadExitosamente() {
        DisponibilidadDTO requestDto = new DisponibilidadDTO(null, 101L, true);

        doNothing().when(habitacionClient).verificarHabitacionExiste(101L);

        Disponibilidad entidadGuardada = new Disponibilidad();
        entidadGuardada.setId(1L);
        entidadGuardada.setHabitacionId(101L);
        entidadGuardada.setDisponible(true);

        when(repository.save(any(Disponibilidad.class))).thenReturn(entidadGuardada);

        DisponibilidadDTO resultado = disponibilidadService.guardar(requestDto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(101L, resultado.getHabitacionId());
        assertTrue(resultado.getDisponible());
        
        verify(habitacionClient, times(1)).verificarHabitacionExiste(101L);
        verify(repository, times(1)).save(any(Disponibilidad.class));
    }

    // ==========================================
    // 2. TEST: GUARDAR DISPONIBILIDAD (FALLO / INTEGRACIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar IllegalArgumentException cuando el cliente HTTP reporta que la habitación no existe")
    void deberiaLanzarExcepcionCuandoHabitacionNoExiste() {
        DisponibilidadDTO requestDto = new DisponibilidadDTO(null, 999L, true);

        doThrow(new RuntimeException("404 Not Found"))
            .when(habitacionClient).verificarHabitacionExiste(999L);

        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            disponibilidadService.guardar(requestDto);
        });

        assertTrue(excepcion.getMessage().contains("Error de Integración"));
        verify(repository, times(0)).save(any(Disponibilidad.class));
    }

    // ==========================================
    // 3. TEST: LISTAR TODAS LAS DISPONIBILIDADES
    // ==========================================
    @Test
    @DisplayName("Debería retornar una lista con todos los DTOs de disponibilidad mapeados")
    void deberiaListarTodasLasDisponibilidades() {
        Disponibilidad d1 = new Disponibilidad();
        d1.setId(1L); d1.setHabitacionId(101L); d1.setDisponible(true);

        Disponibilidad d2 = new Disponibilidad();
        d2.setId(2L); d2.setHabitacionId(102L); d2.setDisponible(false);

        when(repository.findAll()).thenReturn(List.of(d1, d2));

        List<DisponibilidadDTO> resultado = disponibilidadService.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    // ==========================================
    // 4. TEST: OBTENER POR HABITACIÓN (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería retornar la disponibilidad correcta buscando por el ID de la habitación")
    void deberiaObtenerPorHabitacionExitosamente() {
        Long habitacionId = 105L;
        Disponibilidad disponibilidadMock = new Disponibilidad();
        disponibilidadMock.setId(10L);
        disponibilidadMock.setHabitacionId(habitacionId);
        disponibilidadMock.setDisponible(true);

        when(repository.findByHabitacionId(habitacionId)).thenReturn(Optional.of(disponibilidadMock));

        DisponibilidadDTO resultado = disponibilidadService.obtenerPorHabitacion(habitacionId);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        verify(repository, times(1)).findByHabitacionId(habitacionId);
    }

    // ==========================================
    // 5. TEST: OBTENER POR HABITACIÓN (EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar IllegalArgumentException cuando la habitación no tiene registros en la BD")
    void deberiaLanzarExcepcionCuandoNoSeEncuentraRegistro() {
        Long habitacionInexistente = 404L;
        when(repository.findByHabitacionId(habitacionInexistente)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            disponibilidadService.obtenerPorHabitacion(habitacionInexistente);
        });

        verify(repository, times(1)).findByHabitacionId(habitacionInexistente);
    }
}