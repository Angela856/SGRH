package cl.duocuc.dsy1103.comentario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.duocuc.dsy1103.comentario.DTO.ComentarioDTO;
import cl.duocuc.dsy1103.comentario.Model.Comentario;
import cl.duocuc.dsy1103.comentario.Repository.ComentarioRepository;
import cl.duocuc.dsy1103.comentario.Service.ComentarioService;


@ExtendWith(MockitoExtension.class)
class ComentarioServiceTest {

    @Mock
    private ComentarioRepository repository;

    @InjectMocks
    private ComentarioService comentarioService;

    // ==========================================
    // 1. TEST: GUARDAR COMENTARIO (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería guardar exitosamente un comentario y retornar su DTO")
    void deberiaGuardarComentarioExitosamente() {
        // GIVEN
        ComentarioDTO requestDto = new ComentarioDTO(null, 101L, 5L, "Excelente servicio e instalaciones.", Integer.valueOf(5));

        Comentario comentarioGuardado = new Comentario();
        comentarioGuardado.setId(1L);
        comentarioGuardado.setHabitacionId(101L);
        comentarioGuardado.setHuespedId(5L);
        comentarioGuardado.setTexto("Excelente servicio e instalaciones.");
        comentarioGuardado.setCalificacion(Integer.valueOf(5));

        when(repository.save(any(Comentario.class))).thenReturn(comentarioGuardado);

        // WHEN
        ComentarioDTO resultado = comentarioService.guardar(requestDto);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(101L, resultado.getHabitacionId());
        assertEquals(5L, resultado.getHuespedId());
        assertEquals("Excelente servicio e instalaciones.", resultado.getTexto());
        assertEquals(Integer.valueOf(5), resultado.getCalificacion());

        verify(repository, times(1)).save(any(Comentario.class));
    }

    // ==========================================
    // 2. TEST: LISTAR TODOS LOS COMENTARIOS
    // ==========================================
    @Test
    @DisplayName("Debería retornar una lista con todos los comentarios registrados en el hotel")
    void deberiaListarTodosLosComentarios() {
        // GIVEN
        Comentario c1 = new Comentario();
        c1.setId(1L); c1.setHabitacionId(101L); c1.setHuespedId(1L); c1.setTexto("Bueno"); c1.setCalificacion(Integer.valueOf(4));

        Comentario c2 = new Comentario();
        c2.setId(2L); c2.setHabitacionId(102L); c2.setHuespedId(2L); c2.setTexto("Malo"); c2.setCalificacion(Integer.valueOf(2));

        when(repository.findAll()).thenReturn(List.of(c1, c2));

        // WHEN
        List<ComentarioDTO> resultado = comentarioService.listar();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Bueno", resultado.get(0).getTexto());
        assertEquals("Malo", resultado.get(1).getTexto());

        verify(repository, times(1)).findAll();
    }

    // ==========================================
    // 3. TEST: LISTAR POR HABITACIÓN (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería retornar los comentarios de una habitación específica")
    void deberiaListarPorHabitacionExitosamente() {
        // GIVEN
        Long habitacionId = 202L;
        Comentario mockComentario = new Comentario();
        mockComentario.setId(5L);
        mockComentario.setHabitacionId(habitacionId);
        mockComentario.setHuespedId(10L);
        mockComentario.setTexto("Muy cómoda la cama");
        mockComentario.setCalificacion(Integer.valueOf(5));

        when(repository.findByHabitacionId(habitacionId)).thenReturn(List.of(mockComentario));

        // WHEN
        List<ComentarioDTO> resultado = comentarioService.listarPorHabitacion(habitacionId);

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(habitacionId, resultado.get(0).getHabitacionId());
        assertEquals("Muy cómoda la cama", resultado.get(0).getTexto());

        verify(repository, times(1)).findByHabitacionId(habitacionId);
    }

    // ==========================================
    // 4. TEST: LISTAR POR HABITACIÓN (EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar IllegalArgumentException si la habitación no registra comentarios")
    void deberiaLanzarExcepcionCuandoNoHayComentariosEnHabitacion() {
        // GIVEN
        Long habitacionSinComentarios = 303L;
        when(repository.findByHabitacionId(habitacionSinComentarios)).thenReturn(Collections.emptyList());

        // WHEN & THEN
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            comentarioService.listarPorHabitacion(habitacionSinComentarios);
        });

        assertTrue(excepcion.getMessage().contains("No se encontraron comentarios registrados"));

        verify(repository, times(1)).findByHabitacionId(habitacionSinComentarios);
    }
}