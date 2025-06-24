package com.duocshop.duocshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.duocshop.duocshop.model.Categoria;
import com.duocshop.duocshop.model.CategoriaProducto;
import com.duocshop.duocshop.repository.CategoriaRepository;
import com.duocshop.duocshop.repository.CategoriaProductoRepository;

@SpringBootTest
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @MockBean
    private CategoriaProductoRepository categoriaProductoRepository;

    private Categoria crearCategoria() {
        return new Categoria(1, "Electrónicos", new ArrayList<CategoriaProducto>());
    }

    @Test
    public void testObtenerCategorias() {
        when(categoriaRepository.findAll())
                .thenReturn(List.of(crearCategoria()));

        List<Categoria> categorias = categoriaService.obtenerCategorias();
        assertNotNull(categorias);
        assertEquals(1, categorias.size());
    }

    @Test
    public void testObtenerCategoriaPorId() {
        when(categoriaRepository.findById(1))
                .thenReturn(Optional.of(crearCategoria()));

        Categoria categoria = categoriaService.obtenerCategoriaPorId(1);
        assertNotNull(categoria);
        assertEquals("Electrónicos", categoria.getNombre());
    }

    @Test
    public void testGuardarCategoria() {
        Categoria categoria = crearCategoria();
        when(categoriaRepository.save(categoria))
                .thenReturn(categoria);

        Categoria categoriaGuardada = categoriaService.guardarCategoria(categoria);
        assertNotNull(categoriaGuardada);
        assertEquals("Electrónicos", categoriaGuardada.getNombre());
    }

    @Test
    public void testActualizarCategoria(){
        Categoria existingCategoria = crearCategoria();
        Categoria patchData = new Categoria();
        patchData.setNombre("Tecnología");
        
        // Crear un objeto actualizado con el nuevo nombre
        Categoria updatedCategoria = new Categoria(
                1,
                "Tecnología",
                new ArrayList<CategoriaProducto>());

        when(categoriaRepository.findById(1))
        .thenReturn(Optional.of(existingCategoria));
        when(categoriaRepository.save(any(Categoria.class)))
        .thenReturn(updatedCategoria);

        Categoria patchedCategoria = categoriaService.actualizarCategoria(1, patchData);
        assertNotNull(patchedCategoria);
        assertEquals("Tecnología", patchedCategoria.getNombre());
    }

    @Test
    public void testEliminarCategoria(){
        Categoria categoria = crearCategoria();
        when(categoriaRepository.findById(1))
                .thenReturn(Optional.of(categoria));
        doNothing().when(categoriaProductoRepository).deleteByCategoria(categoria);
        doNothing().when(categoriaRepository).delete(categoria);
        
        categoriaService.eliminarCategoria(1);
        
        verify(categoriaRepository, times(1)).findById(1);
        verify(categoriaProductoRepository, times(1)).deleteByCategoria(categoria);
        verify(categoriaRepository, times(1)).delete(categoria);
    }

    @Test
    public void testEliminarCategoria_NoEncontrado() {
        when(categoriaRepository.findById(999))
                .thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            categoriaService.eliminarCategoria(999);
        });
        
        verify(categoriaRepository, times(1)).findById(999);
        verify(categoriaRepository, never()).delete(any());
    }
}
