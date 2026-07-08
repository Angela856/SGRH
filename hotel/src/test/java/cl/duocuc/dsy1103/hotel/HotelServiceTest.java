package cl.duocuc.dsy1103.hotel;

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

import cl.duocuc.dsy1103.hotel.dto.HotelRequest;
import cl.duocuc.dsy1103.hotel.dto.HotelResponse;
import cl.duocuc.dsy1103.hotel.mapper.HotelMapper;
import cl.duocuc.dsy1103.hotel.model.Hotel;
import cl.duocuc.dsy1103.hotel.repository.HotelRepository;
import cl.duocuc.dsy1103.hotel.service.HotelService;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository repository;

    @Mock
    private HotelMapper mapper;

    @InjectMocks
    private HotelService hotelService;

    // ==========================================
    // 1. TEST: OBTENER TODOS LOS HOTELES/HABITACIONES
    // ==========================================
    @Test
    @DisplayName("Debería retornar una lista con todas las habitaciones de hotel")
    void deberiaObtenerTodosLosHoteles() {
        // GIVEN
        Hotel h1 = new Hotel();
        Hotel h2 = new Hotel();
        when(repository.findAll()).thenReturn(List.of(h1, h2));
        when(mapper.toResponse(any(Hotel.class))).thenReturn(new HotelResponse());

        // WHEN
        List<HotelResponse> resultado = hotelService.obtenerTodosLosHoteles();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    // ==========================================
    // 2. TEST: OBTENER POR ID (CASO ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería obtener una habitación específica por su ID")
    void deberiaObtenerHotelPorIdExitosamente() {
        // GIVEN
        Long idBusqueda = 1L;
        Hotel hotelMock = new Hotel();
        hotelMock.setId(idBusqueda);

        HotelResponse responseMock = HotelResponse.builder()
                .id(idBusqueda)
                .nombre("Hotel Duoc Plaza")
                .numeroHabitacion("Suite 402")
                .precioPorNoche(65000.00)
                .disponible(true)
                .build();

        when(repository.findById(idBusqueda)).thenReturn(Optional.of(hotelMock));
        when(mapper.toResponse(hotelMock)).thenReturn(responseMock);

        // WHEN
        HotelResponse resultado = hotelService.obtenerHotelPorId(idBusqueda);

        // THEN
        assertNotNull(resultado);
        assertEquals(idBusqueda, resultado.getId());
        assertEquals("Suite 402", resultado.getNumeroHabitacion());
        verify(repository, times(1)).findById(idBusqueda);
    }

    // ==========================================
    // 3. TEST: OBTENER POR ID (CASO EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar NoSuchElementException cuando el ID de habitación no existe")
    void deberiaLanzarExcepcionCuandoHotelNoExiste() {
        // GIVEN
        Long idInexistente = 99L;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            hotelService.obtenerHotelPorId(idInexistente);
        });
        verify(repository, times(1)).findById(idInexistente);
    }

    // ==========================================
    // 4. TEST: CREAR HABITACIÓN/HOTEL
    // ==========================================
    @Test
    @DisplayName("Debería registrar una nueva habitación de hotel exitosamente")
    void deberiaCrearHotelExitosamente() {
        // GIVEN
        HotelRequest request = new HotelRequest("Hotel Duoc Plaza", "Av. España 1680", "4 Estrellas", "Suite 402", 65000.00, true);
        Hotel hotelEntidad = new Hotel();

        when(mapper.toEntity(request)).thenReturn(hotelEntidad);
        when(repository.save(hotelEntidad)).thenReturn(hotelEntidad);

        HotelResponse responseMock = HotelResponse.builder()
                .id(1L)
                .nombre(request.getNombre())
                .numeroHabitacion(request.getNumeroHabitacion())
                .precioPorNoche(request.getPrecioPorNoche())
                .build();
        when(mapper.toResponse(hotelEntidad)).thenReturn(responseMock);

        // WHEN
        HotelResponse resultado = hotelService.crearHotel(request);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Suite 402", resultado.getNumeroHabitacion());
        verify(repository, times(1)).save(hotelEntidad);
    }

    // ==========================================
    // 5. TEST: ACTUALIZAR HABITACIÓN/HOTEL
    // ==========================================
    @Test
    @DisplayName("Debería actualizar todos los campos lógicos de una habitación existente")
    void deberiaActualizarHotelExitosamente() {
        // GIVEN
        Long idExistente = 1L;
        HotelRequest requestUpdate = new HotelRequest("Hotel Duoc Plaza", "Av. España 1680", "4 Estrellas", "Suite 402 Modificada", 70000.00, false);

        Hotel hotelAntiguo = new Hotel();
        hotelAntiguo.setId(idExistente);

        Hotel hotelActualizado = new Hotel();
        hotelActualizado.setId(idExistente);

        HotelResponse responseMock = HotelResponse.builder()
                .id(idExistente)
                .numeroHabitacion("Suite 402 Modificada")
                .precioPorNoche(70000.00)
                .disponible(false)
                .build();

        when(repository.findById(idExistente)).thenReturn(Optional.of(hotelAntiguo));
        when(repository.save(any(Hotel.class))).thenReturn(hotelActualizado);
        when(mapper.toResponse(any(Hotel.class))).thenReturn(responseMock);

        // WHEN
        HotelResponse resultado = hotelService.actualizarHotel(idExistente, requestUpdate);

        // THEN
        assertNotNull(resultado);
        assertEquals("Suite 402 Modificada", resultado.getNumeroHabitacion());
        assertEquals(70000.00, resultado.getPrecioPorNoche());
        verify(repository, times(1)).findById(idExistente);
        verify(repository, times(1)).save(any(Hotel.class));
    }

    // ==========================================
    // 6. TEST: ELIMINAR HABITACIÓN/HOTEL
    // ==========================================
    @Test
    @DisplayName("Debería eliminar el registro si el ID de habitación es válido")
    void deberiaEliminarHotelExitosamente() {
        // GIVEN
        Long idExistente = 1L;
        when(repository.existsById(idExistente)).thenReturn(true);

        // WHEN
        hotelService.eliminarHotel(idExistente);

        // THEN
        verify(repository, times(1)).deleteById(idExistente);
    }
}