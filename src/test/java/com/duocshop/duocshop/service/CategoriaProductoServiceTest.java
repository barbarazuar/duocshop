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

import com.duocshop.duocshop.model.Categoria;
import com.duocshop.duocshop.model.CategoriaProducto;
import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.repository.CategoriaProductoRepository;

@SpringBootTest
public class CategoriaProductoServiceTest {

    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @MockBean
    private CategoriaProductoRepository categoriaProductoRepository;

    private CategoriaProducto crearCategoriaProducto() {
        return new CategoriaProducto(
                1,
                new Categoria(),
                new Producto());
    }

    @Test
    public void testObtenerCategoriaProductos() {
        when(categoriaProductoRepository.findAll())
                .thenReturn(List.of(crearCategoriaProducto()));
        
        List<CategoriaProducto> categoriaProductos = categoriaProductoService.obtenerCategoriaProductos();
        
        assertNotNull(categoriaProductos);
        assertEquals(1, categoriaProductos.size());
        verify(categoriaProductoRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerCategoriaProductoPorId() {
        when(categoriaProductoRepository.findById(1))
                .thenReturn(Optional.of(crearCategoriaProducto()));
        
        CategoriaProducto categoriaProducto = categoriaProductoService.obtenerCategoriaProductoPorId(1);
        
        assertNotNull(categoriaProducto);
        assertEquals(1, categoriaProducto.getId());
        verify(categoriaProductoRepository, times(1)).findById(1);
    }

    @Test
    public void testGuardarCategoriaProducto() {
        CategoriaProducto categoriaProducto = crearCategoriaProducto();
        when(categoriaProductoRepository.save(categoriaProducto))
                .thenReturn(categoriaProducto);
        
        CategoriaProducto categoriaProductoGuardado = categoriaProductoService.guardarCategoriaProducto(categoriaProducto);
        
        assertNotNull(categoriaProductoGuardado);
        assertEquals(1, categoriaProductoGuardado.getId());
        verify(categoriaProductoRepository, times(1)).save(categoriaProducto);
    }

    @Test
    public void testActualizarCategoriaProducto() {
        CategoriaProducto categoriaProducto = crearCategoriaProducto();
        when(categoriaProductoRepository.findById(categoriaProducto.getId()))
                .thenReturn(Optional.of(categoriaProducto));
        when(categoriaProductoRepository.save(categoriaProducto))
                .thenReturn(categoriaProducto);
        
        CategoriaProducto categoriaProductoActualizado = categoriaProductoService.actualizarCategoriaProducto(categoriaProducto.getId(), categoriaProducto);
        
        assertNotNull(categoriaProductoActualizado);
        assertEquals(1, categoriaProductoActualizado.getId());
        verify(categoriaProductoRepository, times(1)).save(categoriaProducto);
    }

    @Test
    public void testEliminarCategoriaProducto() {
        CategoriaProducto categoriaProducto = crearCategoriaProducto();
        when(categoriaProductoRepository.findById(1))
                .thenReturn(Optional.of(categoriaProducto));
        doNothing().when(categoriaProductoRepository).delete(categoriaProducto);
        
        categoriaProductoService.eliminarCategoriaProducto(1);
        
        verify(categoriaProductoRepository, times(1)).findById(1);
        verify(categoriaProductoRepository, times(1)).delete(categoriaProducto);
    }

    @Test
    public void testEliminarCategoriaProducto_NoEncontrado() {
        when(categoriaProductoRepository.findById(999))
                .thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            categoriaProductoService.eliminarCategoriaProducto(999);
        });
        
        verify(categoriaProductoRepository, times(1)).findById(999);
        verify(categoriaProductoRepository, never()).delete(any());
    }
}
