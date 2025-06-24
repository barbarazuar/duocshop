package com.duocshop.duocshop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.duocshop.duocshop.model.Sede;
import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.repository.UsuarioRepository;
import com.duocshop.duocshop.repository.UsuarioProductoRepository;
import com.duocshop.duocshop.repository.UsuarioCalificacionRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private UsuarioProductoRepository usuarioProductoRepository;

    @MockBean
    private UsuarioCalificacionRepository usuarioCalificacionRepository;

    private Usuario crearUsuario() {
        return new Usuario(
                1,
                "Juan",
                "Perez",
                "juan.perez@gmail.com",
                "123456",
                new Sede(),
                null);
    }

    @Test
    public void testObtenerUsuarios() {
        when(usuarioRepository.findAll())
                .thenReturn(List.of(crearUsuario()));

        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testObtenerUsuarioPorId() {
        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(crearUsuario()));

        Usuario usuario = usuarioService.obtenerUsuarioPorId(1);
        assertNotNull(usuario);
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Perez", usuario.getApellido());
    }

    @Test
    public void testGuardarUsuario() {
        Usuario usuario = crearUsuario();
        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
        assertNotNull(usuarioGuardado);
        assertEquals("Juan", usuarioGuardado.getNombre());
        assertEquals("Perez", usuarioGuardado.getApellido());

    }

    @Test
    public void testActualizarUsuario() {
        Usuario existingUsuario = crearUsuario();
        Usuario patchData = new Usuario();
        patchData.setNombre("Juan");

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(existingUsuario));
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(existingUsuario);

        Usuario patchedUsuario = usuarioService.actualizarUsuario(1, patchData);
        assertNotNull(patchedUsuario);
        assertEquals("Juan", patchedUsuario.getNombre());
    }

    @Test
    public void testEliminarUsuario() {
        Usuario usuario = crearUsuario();
        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));
        doNothing().when(usuarioProductoRepository).deleteByUsuario(usuario);
        doNothing().when(usuarioCalificacionRepository).deleteByUsuario(usuario);
        doNothing().when(usuarioRepository).delete(usuario);
        
        usuarioService.eliminarUsuario(1);
        
        verify(usuarioRepository, times(1)).findById(1);
        verify(usuarioProductoRepository, times(1)).deleteByUsuario(usuario);
        verify(usuarioCalificacionRepository, times(1)).deleteByUsuario(usuario);
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    public void testEliminarUsuario_NoEncontrado() {
        when(usuarioRepository.findById(999))
                .thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            usuarioService.eliminarUsuario(999);
        });
        
        verify(usuarioRepository, times(1)).findById(999);
        verify(usuarioRepository, never()).delete(any());
    }
}
