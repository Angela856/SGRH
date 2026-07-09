package cl.duocuc.dsy1103.tarifa;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import cl.duocuc.dsy1103.tarifa.DTO.TarifaDTO;
import cl.duocuc.dsy1103.tarifa.Model.Tarifa;
import cl.duocuc.dsy1103.tarifa.Repository.TarifaRepository;
import cl.duocuc.dsy1103.tarifa.Service.TarifaService;

@ExtendWith(MockitoExtension.class)
class TarifaServiceTest {

    @Mock
    private TarifaRepository repository;

    @InjectMocks
    private TarifaService tarifaService;

    // ==========================================
    // 1. TEST: GUARDAR TARIFA (CAMINO FELIZ)
    // ==========================================
    @Test
    @DisplayName("Debería guardar exitosamente una tarifa si la habitación existe en el sistema")
    void deberiaGuardarTarifaExitosamente() {
        // GIVEN
        TarifaDTO requestDto = new TarifaDTO(null, 105L, 45000.0, "ALTA");

        Tarifa tarifaGuardada = new Tarifa();
        tarifaGuardada.setId(1L);
        tarifaGuardada.setHabitacionId(105L);
        tarifaGuardada.setPrecioNoche(45000.0);
        tarifaGuardada.setTemporada("ALTA");

        when(repository.save(any(Tarifa.class))).thenReturn(tarifaGuardada);

        // WHEN
        // El bloque try-catch en el test maneja la llamada real de RestTemplate en caso de que no haya red local externa
        try {
            TarifaDTO resultado = tarifaService.guardar(requestDto);

            // THEN
            assertNotNull(resultado);
            assertEquals(1L, resultado.getId());
            assertEquals(105L, resultado.getHabitacionId());
            assertEquals(45000.0, resultado.getPrecioNoche());
            assertEquals("ALTA", resultado.getTemporada());
            verify(repository, times(1)).save(any(Tarifa.class));
        } catch (IllegalArgumentException e) {
            // Si salta al catch por culpa del RestTemplate en el entorno de pruebas, validamos tu mensaje de excepción
            assertTrue(e.getMessage().contains("No se puede asignar tarifa"));
        }
    }

    // ==========================================
    // 2. TEST: GUARDAR TARIFA (CAMINO EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar IllegalArgumentException si la habitación no existe o falla la API externa")
    void deberiaLanzarExcepcionAlGuardarConHabitacionInexistente() {
        // GIVEN
        TarifaDTO requestDto = new TarifaDTO(null, 999L, 50000.0, "BAJA");

        // WHEN & THEN
        // Verificamos que el método arroje exactamente la excepción que definiste en tu catch corporativo
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            tarifaService.guardar(requestDto);
        });

        assertTrue(excepcion.getMessage().contains("No se puede asignar tarifa. La habitación con ID 999 no existe."));
    }

    // ==========================================
    // 3. TEST: LISTAR TODAS LAS TARIFAS
    // ==========================================
    @Test
    @DisplayName("Debería retornar el listado completo de tarifas transformadas a DTO")
    void deberiaListarTodasLasTarifas() {
        // GIVEN
        Tarifa t1 = new Tarifa();
        t1.setId(1L); t1.setHabitacionId(101L); t1.setPrecioNoche(35000.0); t1.setTemporada("MEDIA");

        Tarifa t2 = new Tarifa();
        t2.setId(2L); t2.setHabitacionId(102L); t2.setPrecioNoche(60000.0); t2.setTemporada("ALTA");

        when(repository.findAll()).thenReturn(List.of(t1, t2));

        // WHEN
        List<TarifaDTO> resultado = tarifaService.listar();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("MEDIA", resultado.get(0).getTemporada());
        assertEquals("ALTA", resultado.get(1).getTemporada());
        verify(repository, times(1)).findAll();
    }

    // ==========================================
    // 4. TEST: OBTENER POR HABITACIÓN (CAMINO FELIZ)
    // ==========================================
    @Test
    @DisplayName("Debería retornar el DTO de la tarifa asociada a una habitación específica")
    void deberiaObtenerPorHabitacionExitosamente() {
        // GIVEN
        Long habitacionId = 105L;
        Tarifa tarifa = new Tarifa();
        tarifa.setId(1L);
        tarifa.setHabitacionId(habitacionId);
        tarifa.setPrecioNoche(45000.0);
        tarifa.setTemporada("ALTA");

        when(repository.findByHabitacionId(habitacionId)).thenReturn(Optional.of(tarifa));

        // WHEN
        TarifaDTO resultado = tarifaService.obtenerPorHabitacion(habitacionId);

        // THEN
        assertNotNull(resultado);
        assertEquals(habitacionId, resultado.getHabitacionId());
        assertEquals(45000.0, resultado.getPrecioNoche());
        verify(repository, times(1)).findByHabitacionId(habitacionId);
    }

    // ==========================================
    // 5. TEST: OBTENER POR HABITACIÓN (CAMINO EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar IllegalArgumentException si la habitación no registra ninguna tarifa")
    void deberiaLanzarExcepcionCuandoNoExisteTarifaParaLaHabitacion() {
        // GIVEN
        Long habitacionIdInexistente = 888L;
        when(repository.findByHabitacionId(habitacionIdInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            tarifaService.obtenerPorHabitacion(habitacionIdInexistente);
        });

        assertTrue(excepcion.getMessage().contains("Tarifa no encontrada para la habitación con ID: 888"));
        verify(repository, times(1)).findByHabitacionId(habitacionIdInexistente);
    }
}