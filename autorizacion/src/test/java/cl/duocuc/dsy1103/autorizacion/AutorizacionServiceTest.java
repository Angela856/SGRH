package cl.duocuc.dsy1103.autorizacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import cl.duocuc.dsy1103.autorizacion.dto.AuthRequest;
import cl.duocuc.dsy1103.autorizacion.dto.AuthResponse;
import cl.duocuc.dsy1103.autorizacion.mapper.AuthMapper;
import cl.duocuc.dsy1103.autorizacion.model.Usuario;
import cl.duocuc.dsy1103.autorizacion.repository.UsuarioRepository;
import cl.duocuc.dsy1103.autorizacion.service.AutorizacionService;

@ExtendWith(MockitoExtension.class)
class AutorizacionServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private AuthMapper mapper;

    @InjectMocks
    private AutorizacionService autorizacionService;

    // ==========================================
    // 1. TEST: OBTENER TODOS LOS USUARIOS
    // ==========================================
    @Test
    @DisplayName("Debería retornar una lista con todos los usuarios registrados")
    void deberiaObtenerTodosLosUsuarios() {
        // GIVEN
        Usuario u1 = new Usuario();
        Usuario u2 = new Usuario();
        when(repository.findAll()).thenReturn(List.of(u1, u2));
        when(mapper.toResponse(any(Usuario.class))).thenReturn(new AuthResponse());

        // WHEN
        List<AuthResponse> resultado = autorizacionService.obtenerTodosLosUsuarios();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    // ==========================================
    // 2. TEST: OBTENER USUARIO POR ID (ÉXITO)
    // ==========================================
    @Test
    @DisplayName("Debería obtener un usuario específico por su ID")
    void deberiaObtenerUsuarioPorIdExitosamente() {
        // GIVEN
        Long idBusqueda = 1L;
        Usuario usuarioMock = new Usuario(idBusqueda, "Jean", "alumno@duocuc.cl", "pass123", "ADMIN");

        AuthResponse responseMock = AuthResponse.builder()
                .id(idBusqueda)
                .correo("alumno@duocuc.cl")
                .rol("ADMIN")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.findById(idBusqueda)).thenReturn(Optional.of(usuarioMock));
        when(mapper.toResponse(usuarioMock)).thenReturn(responseMock);

        // WHEN
        AuthResponse resultado = autorizacionService.obtenerUsuarioPorId(idBusqueda);

        // THEN
        assertNotNull(resultado);
        assertEquals(idBusqueda, resultado.getId());
        assertEquals("alumno@duocuc.cl", resultado.getCorreo());
        verify(repository, times(1)).findById(idBusqueda);
    }

    // ==========================================
    // 3. TEST: OBTENER USUARIO POR ID (EXCEPCIÓN)
    // ==========================================
    @Test
    @DisplayName("Debería lanzar NoSuchElementException cuando el ID de usuario no existe")
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        // GIVEN
        Long idInexistente = 99L;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> {
            autorizacionService.obtenerUsuarioPorId(idInexistente);
        });
        verify(repository, times(1)).findById(idInexistente);
    }

    // ==========================================
    // 4. TEST: CREAR USUARIO
    // ==========================================
    @Test
    @DisplayName("Debería registrar un nuevo usuario de forma exitosa")
    void deberiaCrearUsuarioExitosamente() {
        // GIVEN
        AuthRequest request = new AuthRequest("alumno@duocuc.cl", "password123", "ADMIN");
        Usuario usuarioEntidad = new Usuario();

        when(mapper.toEntity(request)).thenReturn(usuarioEntidad);
        when(repository.save(usuarioEntidad)).thenReturn(usuarioEntidad);

        AuthResponse responseMock = AuthResponse.builder()
                .id(1L)
                .correo(request.getCorreo())
                .rol(request.getRol())
                .build();
        when(mapper.toResponse(usuarioEntidad)).thenReturn(responseMock);

        // WHEN
        AuthResponse resultado = autorizacionService.crearUsuario(request);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).save(usuarioEntidad);
    }

    // ==========================================
    // 5. TEST: ACTUALIZAR USUARIO
    // ==========================================
    @Test
    @DisplayName("Debería actualizar los datos de un usuario existente")
    void deberiaActualizarUsuarioExitosamente() {
        // GIVEN
        Long idExistente = 1L;
        AuthRequest requestUpdate = new AuthRequest("jean.modificado@duocuc.cl", "newpass123", "RECEPCIONISTA");
        
        Usuario usuarioAntiguo = new Usuario(idExistente, "Jean", "alumno@duocuc.cl", "pass123", "ADMIN");
        Usuario usuarioActualizado = new Usuario(idExistente, "Jean", "jean.modificado@duocuc.cl", "newpass123", "RECEPCIONISTA");

        AuthResponse responseMock = AuthResponse.builder()
                .id(idExistente)
                .correo("jean.modificado@duocuc.cl")
                .rol("RECEPCIONISTA")
                .build();

        when(repository.findById(idExistente)).thenReturn(Optional.of(usuarioAntiguo));
        when(repository.save(any(Usuario.class))).thenReturn(usuarioActualizado);
        when(mapper.toResponse(any(Usuario.class))).thenReturn(responseMock);

        // WHEN
        AuthResponse resultado = autorizacionService.actualizarUsuario(idExistente, requestUpdate);

        // THEN
        assertNotNull(resultado);
        assertEquals("jean.modificado@duocuc.cl", resultado.getCorreo());
        assertEquals("RECEPCIONISTA", resultado.getRol());
        verify(repository, times(1)).findById(idExistente);
        verify(repository, times(1)).save(any(Usuario.class));
    }

    // ==========================================
    // 6. TEST: ELIMINAR USUARIO
    // ==========================================
    @Test
    @DisplayName("Debería eliminar un usuario si el ID es válido")
    void deberiaEliminarUsuarioExitosamente() {
        // GIVEN
        Long idExistente = 1L;
        when(repository.existsById(idExistente)).thenReturn(true);

        // WHEN
        autorizacionService.eliminarUsuario(idExistente);

        // THEN
        verify(repository, times(1)).deleteById(idExistente);
    }

    // ==========================================
    // 7. TEST: LOGIN (CASO EXITO)
    // ==========================================
    @Test
    @DisplayName("Debería retornar TRUE cuando el correo existe y la contraseña coincide")
    void deberiaRetornarTrueEnLoginExitoso() {
        // GIVEN
        String correo = "alumno@duocuc.cl";
        String contrasena = "correcta123";
        Usuario usuarioMock = new Usuario(1L, "Jean", correo, contrasena, "ADMIN");

        // Simulamos que el repositorio encuentra al usuario por su correo
        when(repository.findByCorreo(correo)).thenReturn(Optional.of(usuarioMock));

        // WHEN
        boolean loginValido = autorizacionService.login(correo, contrasena);

        // THEN
        assertTrue(loginValido);
        verify(repository, times(1)).findByCorreo(correo);
    }

    // ==========================================
    // 8. TEST: LOGIN (CASO CONTRASEÑA INCORRECTA)
    // ==========================================
    @Test
    @DisplayName("Debería retornar FALSE cuando el correo existe pero la contraseña es errónea")
    void deberiaRetornarFalseCuandoContrasenaEsIncorrecta() {
        // GIVEN
        String correo = "alumno@duocuc.cl";
        String contrasenaCorrecta = "correcta123";
        String contrasenaErronea = "incorrecta999";
        Usuario usuarioMock = new Usuario(1L, "Jean", correo, contrasenaCorrecta, "ADMIN");

        when(repository.findByCorreo(correo)).thenReturn(Optional.of(usuarioMock));

        // WHEN
        boolean loginValido = autorizacionService.login(correo, contrasenaErronea);

        // THEN
        assertFalse(loginValido);
        verify(repository, times(1)).findByCorreo(correo);
    }

    // ==========================================
    // 9. TEST: LOGIN (CASO CORREO NO EXISTE)
    // ==========================================
    @Test
    @DisplayName("Debería retornar FALSE cuando el correo ingresado no está registrado")
    void deberiaRetornarFalseCuandoCorreoNoExiste() {
        // GIVEN
        String correoInexistente = "no-existo@duocuc.cl";
        when(repository.findByCorreo(correoInexistente)).thenReturn(Optional.empty());

        // WHEN
        boolean loginValido = autorizacionService.login(correoInexistente, "cualquiera123");

        // THEN
        assertFalse(loginValido);
        verify(repository, times(1)).findByCorreo(correoInexistente);
    }
}
