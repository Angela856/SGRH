package cl.duocuc.dsy1103.habitacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

import cl.duocuc.dsy1103.habitacion.DTO.HabitacionDTO;
import cl.duocuc.dsy1103.habitacion.Model.Habitacion;
import cl.duocuc.dsy1103.habitacion.Repository.HabitacionRepository;
import cl.duocuc.dsy1103.habitacion.Service.HabitacionService; // <-- Importamos el Service faltante

@ExtendWith(MockitoExtension.class)
class HabitacionsServiceTest {

    @Mock
    private HabitacionRepository repository;

    @InjectMocks
    private HabitacionService habitacionService;

    // ==========================================
    // 1. TEST: GUARDAR HABITACIÓN (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería guardar exitosamente una habitación y retornar su DTO")
    void deberiaGuardarHabitacionExitosamente() {
        // GIVEN
        HabitacionDTO requestDto = new HabitacionDTO(null, 10L, "305-A", "Suite Executive", 75000.00);

        Habitacion habitacionGuardada = new Habitacion();
        habitacionGuardada.setId(1L);
        habitacionGuardada.setHotelId(10L);
        habitacionGuardada.setNumero("305-A");
        habitacionGuardada.setTipo("Suite Executive");
        habitacionGuardada.setPrecioBase(75000.00);

        when(repository.save(any(Habitacion.class))).thenReturn(habitacionGuardada);

        // WHEN
        HabitacionDTO resultado = habitacionService.guardar(requestDto);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getHotelId());
        assertEquals("305-A", resultado.getNumero());
        assertEquals("Suite Executive", resultado.getTipo());
        assertEquals(75000.00, resultado.getPrecioBase());

        verify(repository, times(1)).save(any(Habitacion.class));
    }

    // ==========================================
    // 2. TEST: LISTAR HABITACIONES POR HOTEL
    // ==========================================
    @Test
    @DisplayName("Debería retornar las habitaciones pertenecientes a un hotel específico")
    void deberiaListarHabitacionesPorHotel() {
        // GIVEN
        Long hotelId = 10L;
        Habitacion h1 = new Habitacion();
        h1.setId(1L); h1.setHotelId(hotelId); h1.setNumero("101"); h1.setTipo("Simple"); h1.setPrecioBase(35000.0);

        Habitacion h2 = new Habitacion();
        h2.setId(2L); h2.setHotelId(hotelId); h2.setNumero("102"); h2.setTipo("Doble"); h2.setPrecioBase(50000.0);

        when(repository.findByHotelId(hotelId)).thenReturn(List.of(h1, h2));

        // WHEN
        List<HabitacionDTO> resultado = habitacionService.listarPorHotel(hotelId);

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("101", resultado.get(0).getNumero());
        assertEquals("102", resultado.get(1).getNumero());

        verify(repository, times(1)).findByHotelId(hotelId);
    }

    // ==========================================
    // 3. TEST: BUSCAR POR ID (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería retornar el DTO de la habitación cuando el ID existe")
    void deberiaBuscarPorIdExitosamente() {
        // GIVEN
        Long habitacionId = 1L;
        Habitacion h = new Habitacion();
        h.setId(habitacionId);
        h.setHotelId(10L);
        h.setNumero("204");
        h.setTipo("Suite");
        h.setPrecioBase(90000.0);

        when(repository.findById(habitacionId)).thenReturn(Optional.of(h));

        // WHEN
        HabitacionDTO resultado = habitacionService.buscarPorId(habitacionId);

        // THEN
        assertNotNull(resultado);
        assertEquals(habitacionId, resultado.getId());
        assertEquals("204", resultado.getNumero());

        verify(repository, times(1)).findById(habitacionId);
    }

    // ==========================================
    // 4. TEST: BUSCAR POR ID (EXCEPCIÓN 404)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar IllegalArgumentException cuando el ID de la habitación no existe")
    void deberiaLanzarExcepcionCuandoIdNoExiste() {
        // GIVEN
        Long idInexistente = 99L;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            habitacionService.buscarPorId(idInexistente);
        });

        assertTrue(excepcion.getMessage().contains("no existe en el sistema"));

        verify(repository, times(1)).findById(idInexistente);
    }

    // ==========================================
    // 5. TEST: ELIMINAR (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería eliminar la habitación de la BD si el ID existe")
    void deberiaEliminarHabitacionExitosamente() {
        // GIVEN
        Long idAEliminar = 1L;
        when(repository.existsById(idAEliminar)).thenReturn(true);
        doNothing().when(repository).deleteById(idAEliminar);

        // WHEN
        habitacionService.eliminar(idAEliminar);

        // THEN
        verify(repository, times(1)).existsById(idAEliminar);
        verify(repository, times(1)).deleteById(idAEliminar);
    }

    // ==========================================
    // 6. TEST: ELIMINAR (FALLO / EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar una excepción al intentar eliminar un ID que no existe")
    void deberiaLanzarExcepcionAlEliminarInexistente() {
        // GIVEN
        Long idFalso = 99L;
        when(repository.existsById(idFalso)).thenReturn(false);

        // WHEN & THEN
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            habitacionService.eliminar(idFalso);
        });

        assertTrue(excepcion.getMessage().contains("No se puede eliminar"));
        verify(repository, times(1)).existsById(idFalso);
        verify(repository, times(0)).deleteById(idFalso); // Verifica que NUNCA intentó borrarlo
    }
}