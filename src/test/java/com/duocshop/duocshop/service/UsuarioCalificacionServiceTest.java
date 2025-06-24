package com.duocshop.duocshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.duocshop.duocshop.model.Calificacion;
import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.model.UsuarioCalificacion;
import com.duocshop.duocshop.repository.UsuarioCalificacionRepository;

@SpringBootTest
public class UsuarioCalificacionServiceTest {

    @Autowired
    private UsuarioCalificacionService usuarioCalificacionService;

    @MockBean
    private UsuarioCalificacionRepository usuarioCalificacionRepository;

    private UsuarioCalificacion crearUsuarioCalificacion() {
        return new UsuarioCalificacion(
                1,
                new Calificacion(),
                new Usuario());
    }

    @Test
    public void testObtenerUsuarioCalificaciones() {
        when(usuarioCalificacionRepository.findAll())
                .thenReturn(List.of(crearUsuarioCalificacion()));
        
        List<UsuarioCalificacion> usuarioCalificaciones = usuarioCalificacionService.obtenerUsuarioCalificaciones();
        
        assertNotNull(usuarioCalificaciones);
        assertEquals(1, usuarioCalificaciones.size());
        verify(usuarioCalificacionRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerUsuarioCalificacionPorId() {
        when(usuarioCalificacionRepository.findById(1))
                .thenReturn(Optional.of(crearUsuarioCalificacion()));
        
        UsuarioCalificacion usuarioCalificacion = usuarioCalificacionService.obtenerUsuarioCalificacionPorId(1);
        
        assertNotNull(usuarioCalificacion);
        assertEquals(1, usuarioCalificacion.getId());
        verify(usuarioCalificacionRepository, times(1)).findById(1);
    }

    @Test
    public void testObtenerUsuarioCalificacionPorId_NoEncontrado() {
        when(usuarioCalificacionRepository.findById(999))
                .thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            usuarioCalificacionService.obtenerUsuarioCalificacionPorId(999);
        });
        
        verify(usuarioCalificacionRepository, times(1)).findById(999);
    }

    @Test
    public void testGuardarUsuarioCalificacion() {
        UsuarioCalificacion usuarioCalificacion = crearUsuarioCalificacion();
        when(usuarioCalificacionRepository.save(usuarioCalificacion))
                .thenReturn(usuarioCalificacion);
        
        UsuarioCalificacion usuarioCalificacionGuardado = usuarioCalificacionService.guardarUsuarioCalificacion(usuarioCalificacion);
        
        assertNotNull(usuarioCalificacionGuardado);
        assertEquals(1, usuarioCalificacionGuardado.getId());
        verify(usuarioCalificacionRepository, times(1)).save(usuarioCalificacion);
    }

    @Test
    public void testActualizarUsuarioCalificacion() {
        UsuarioCalificacion usuarioCalificacion = crearUsuarioCalificacion();
        when(usuarioCalificacionRepository.findById(usuarioCalificacion.getId()))
                .thenReturn(Optional.of(usuarioCalificacion));
        when(usuarioCalificacionRepository.save(usuarioCalificacion))
                .thenReturn(usuarioCalificacion);
        
        UsuarioCalificacion usuarioCalificacionActualizado = usuarioCalificacionService.actualizarUsuarioCalificacion(usuarioCalificacion.getId(), usuarioCalificacion);
        
        assertNotNull(usuarioCalificacionActualizado);
        assertEquals(1, usuarioCalificacionActualizado.getId());
        verify(usuarioCalificacionRepository, times(1)).save(usuarioCalificacion);
    }

    @Test
    public void testEliminarUsuarioCalificacion() {
        UsuarioCalificacion usuarioCalificacion = crearUsuarioCalificacion();
        when(usuarioCalificacionRepository.findById(1))
                .thenReturn(Optional.of(usuarioCalificacion));
        doNothing().when(usuarioCalificacionRepository).delete(usuarioCalificacion);
        
        usuarioCalificacionService.eliminarUsuarioCalificacion(1);
        
        verify(usuarioCalificacionRepository, times(1)).findById(1);
        verify(usuarioCalificacionRepository, times(1)).delete(usuarioCalificacion);
    }

    @Test
    public void testEliminarUsuarioCalificacion_NoEncontrado() {
        when(usuarioCalificacionRepository.findById(999))
                .thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            usuarioCalificacionService.eliminarUsuarioCalificacion(999);
        });
        
        verify(usuarioCalificacionRepository, times(1)).findById(999);
        verify(usuarioCalificacionRepository, never()).delete(any());
    }

    @Test
    public void testObtenerUsuarioCalificacion() {
        Object[] resultado1 = {1, "Usuario1", 5};
        Object[] resultado2 = {2, "Usuario2", 4};
        List<Object[]> resultados = List.of(resultado1, resultado2);
        
        when(usuarioCalificacionRepository.findUsuarioCalificacion())
                .thenReturn(resultados);
        
        List<Map<String, Object>> lista = usuarioCalificacionService.obtenerUsuarioCalificacion();
        
        assertNotNull(lista);
        assertEquals(2, lista.size());
        
        Map<String, Object> primerElemento = lista.get(0);
        assertEquals(1, primerElemento.get("usuarioCalificacion"));
        assertEquals("Usuario1", primerElemento.get("nombreUsuario"));
        assertEquals(5, primerElemento.get("calificacionUsuario"));
        
        Map<String, Object> segundoElemento = lista.get(1);
        assertEquals(2, segundoElemento.get("usuarioCalificacion"));
        assertEquals("Usuario2", segundoElemento.get("nombreUsuario"));
        assertEquals(4, segundoElemento.get("calificacionUsuario"));
        
        verify(usuarioCalificacionRepository, times(1)).findUsuarioCalificacion();
    }
}
