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
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.model.UsuarioProducto;
import com.duocshop.duocshop.repository.UsuarioProductoRepository;

@SpringBootTest
public class UsuarioProductoServiceTest {

    @Autowired
    private UsuarioProductoService usuarioProductoService;

    @MockBean
    private UsuarioProductoRepository usuarioProductoRepository;

    private UsuarioProducto crearUsuarioProducto() {
        return new UsuarioProducto(
                1,
                new Usuario(),
                new Producto());
    }

    @Test
    public void testObtenerUsuarioProductos() {
        when(usuarioProductoRepository.findAll())
                .thenReturn(List.of(crearUsuarioProducto()));
        
        List<UsuarioProducto> usuarioProductos = usuarioProductoService.obtenerUsuarioProductos();
        
        assertNotNull(usuarioProductos);
        assertEquals(1, usuarioProductos.size());
        verify(usuarioProductoRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerUsuarioProductoPorId() {
        when(usuarioProductoRepository.findById(1))
                .thenReturn(Optional.of(crearUsuarioProducto()));
        
        UsuarioProducto usuarioProducto = usuarioProductoService.obtenerUsuarioProductoPorId(1);
        
        assertNotNull(usuarioProducto);
        assertEquals(1, usuarioProducto.getId());
        verify(usuarioProductoRepository, times(1)).findById(1);
    }

    @Test
    public void testGuardarUsuarioProducto() {
        UsuarioProducto usuarioProducto = crearUsuarioProducto();
        when(usuarioProductoRepository.save(usuarioProducto))
                .thenReturn(usuarioProducto);
        
        UsuarioProducto usuarioProductoGuardado = usuarioProductoService.guardarUsuarioProducto(usuarioProducto);
        
        assertNotNull(usuarioProductoGuardado);
        assertEquals(1, usuarioProductoGuardado.getId());
        verify(usuarioProductoRepository, times(1)).save(usuarioProducto);
    }

    @Test
    public void testActualizarUsuarioProducto() {
        UsuarioProducto usuarioProducto = crearUsuarioProducto();
        when(usuarioProductoRepository.findById(usuarioProducto.getId()))
                .thenReturn(Optional.of(usuarioProducto));
        when(usuarioProductoRepository.save(usuarioProducto))
                .thenReturn(usuarioProducto);
        
        UsuarioProducto usuarioProductoActualizado = usuarioProductoService.actualizarUsuarioProducto(usuarioProducto.getId(), usuarioProducto);
        
        assertNotNull(usuarioProductoActualizado);
        assertEquals(1, usuarioProductoActualizado.getId());
        verify(usuarioProductoRepository, times(1)).save(usuarioProducto);
    }

    @Test
    public void testEliminarUsuarioProducto() {
        UsuarioProducto usuarioProducto = crearUsuarioProducto();
        when(usuarioProductoRepository.findById(1))
                .thenReturn(Optional.of(usuarioProducto));
        doNothing().when(usuarioProductoRepository).delete(usuarioProducto);
        
        usuarioProductoService.eliminarUsuarioProducto(1);
        
        verify(usuarioProductoRepository, times(1)).findById(1);
        verify(usuarioProductoRepository, times(1)).delete(usuarioProducto);
    }

    @Test
    public void testEliminarUsuarioProducto_NoEncontrado() {
        when(usuarioProductoRepository.findById(999))
                .thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            usuarioProductoService.eliminarUsuarioProducto(999);
        });
        
        verify(usuarioProductoRepository, times(1)).findById(999);
        verify(usuarioProductoRepository, never()).delete(any());
    }

}
