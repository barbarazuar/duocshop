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

import com.duocshop.duocshop.model.CategoriaProducto;
import com.duocshop.duocshop.model.Marca;
import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.model.UsuarioProducto;
import com.duocshop.duocshop.repository.ProductoRepository;
import com.duocshop.duocshop.repository.CategoriaProductoRepository;
import com.duocshop.duocshop.repository.UsuarioProductoRepository;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    @MockBean
    private CategoriaProductoRepository categoriaProductoRepository;

    @MockBean
    private UsuarioProductoRepository usuarioProductoRepository;

    private Producto crearProducto() {
        return new Producto(
                1,
                "ProductoTest",
                "DescripcionTest",
                10000,
                new Marca(),
                new ArrayList<CategoriaProducto>(),
                new ArrayList<UsuarioProducto>());
    }

    @Test
    public void testObtenerProductos() {
        when(productoRepository.findAll())
                .thenReturn(List.of(crearProducto()));
        List<Producto> productos = productoService.obtenerProductos();
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testObtenerProductoPorId() {
        when(productoRepository.findById(1))
                .thenReturn(Optional.of(crearProducto()));
        Producto producto = productoService.obtenerProductoPorId(1);
        assertNotNull(producto);
        assertEquals("ProductoTest", producto.getNombre());
    }

    @Test
    public void testGuardarProducto() {
        Producto producto = crearProducto();
        when(productoRepository.save(producto))
                .thenReturn(producto);
        Producto productoGuardado = productoService.guardarProducto(producto);
        assertNotNull(productoGuardado);
        assertEquals("ProductoTest", productoGuardado.getNombre());
    }

    @Test
    public void testActualizarProducto(){
        Producto existingProducto = crearProducto();
        Producto patchData = new Producto();
        patchData.setNombre("ProductoActualizado");

        when(productoRepository.findById(1))
        .thenReturn(Optional.of(existingProducto));
        when(productoRepository.save(any(Producto.class)))
        .thenReturn(existingProducto);

        Producto patchedProducto = productoService.actualizarProducto(1, patchData);
        assertNotNull(patchedProducto);
        assertEquals("ProductoActualizado", patchedProducto.getNombre());
    }

    @Test
    public void testEliminarProducto(){
        Producto producto = crearProducto();
        when(productoRepository.findById(1))
                .thenReturn(java.util.Optional.of(producto));
        doNothing().when(categoriaProductoRepository).deleteByProducto(producto);
        doNothing().when(usuarioProductoRepository).deleteByProducto(producto);
        doNothing().when(productoRepository).delete(producto);
        
        productoService.eliminarProducto(1);
        
        verify(productoRepository, times(1)).findById(1);
        verify(categoriaProductoRepository, times(1)).deleteByProducto(producto);
        verify(usuarioProductoRepository, times(1)).deleteByProducto(producto);
        verify(productoRepository, times(1)).delete(producto);
    }

    @Test
    public void testEliminarProducto_NoEncontrado(){
        when(productoRepository.findById(999))
                .thenReturn(java.util.Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            productoService.eliminarProducto(999);
        });
        
        verify(productoRepository, times(1)).findById(999);
        verify(productoRepository, never()).delete(any());
    }

    @Test   
    public void testObtenerProductosPorMarcaId(){
        Integer marcaId = 1;
        List<Producto> productosEsperados = List.of(crearProducto());
        
        when(productoRepository.findByMarcaId(marcaId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorMarcaId(marcaId);
        
        assertNotNull(productos);
        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findByMarcaId(marcaId);
    }

    @Test   
    public void testObtenerProductosPorCategoriaId(){
        Integer categoriaId = 1;
        List<Producto> productosEsperados = List.of(crearProducto());
        
        when(productoRepository.findByCategoriaId(categoriaId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorCategoriaId(categoriaId);
        
        assertNotNull(productos);
        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findByCategoriaId(categoriaId);
    }

    @Test   
    public void testObtenerProductosPorPrecioBetween(){
        Integer precioMin = 5000;
        Integer precioMax = 15000;
        List<Producto> productosEsperados = List.of(crearProducto());
        
        when(productoRepository.findByPrecioBetween(precioMin, precioMax))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorPrecioBetween(precioMin, precioMax);
        
        assertNotNull(productos);
        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findByPrecioBetween(precioMin, precioMax);
    }

    @Test   
    public void testObtenerProductosPorCalificacionBetween(){
        Integer puntajeMin = 3;
        Integer puntajeMax = 5;
        List<Producto> productosEsperados = List.of(crearProducto());
        
        when(productoRepository.findByCalificacionBetween(puntajeMin, puntajeMax))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorCalificacionBetween(puntajeMin, puntajeMax);
        
        assertNotNull(productos);
        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findByCalificacionBetween(puntajeMin, puntajeMax);
    }

    @Test   
    public void testObtenerProductosPorSedeId(){
        Integer sedeId = 1;
        List<Producto> productosEsperados = List.of(crearProducto());
        
        when(productoRepository.findBySedeId(sedeId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorSedeId(sedeId);
        
        assertNotNull(productos);
        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findBySedeId(sedeId);
    }

    @Test   
    public void testObtenerProductosPorMarcaIdAndCategoriaIdAndSedeId(){
        Integer marcaId = 1;
        Integer categoriaId = 2;
        Integer sedeId = 3;
        List<Producto> productosEsperados = List.of(crearProducto());
        
        when(productoRepository.findByMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId);
        
        assertNotNull(productos);
        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findByMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId);
    }

    @Test   
    public void testObtenerProductosPorSedeIdAndCategoriaId(){
        Integer sedeId = 1;
        Integer categoriaId = 2;
        List<Producto> productosEsperados = List.of(crearProducto());
        
        when(productoRepository.findBySedeIdAndCategoriaId(sedeId, categoriaId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorSedeIdAndCategoriaId(sedeId, categoriaId);
        
        assertNotNull(productos);
        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findBySedeIdAndCategoriaId(sedeId, categoriaId);
    }

    @Test   
    public void testObtenerProductosPorMarcaIdAndSedeId(){
        Integer marcaId = 1;
        Integer sedeId = 2;
        List<Producto> productosEsperados = List.of(crearProducto());
        
        when(productoRepository.findByMarcaIdAndSedeId(marcaId, sedeId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorMarcaIdAndSedeId(marcaId, sedeId);
        
        assertNotNull(productos);
        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findByMarcaIdAndSedeId(marcaId, sedeId);
    }

    @Test   
    public void testObtenerProductosPorPrecioBetween_ListaVacia(){
        Integer precioMin = 5000;
        Integer precioMax = 15000;
        List<Producto> productosEsperados = new ArrayList<>();
        
        when(productoRepository.findByPrecioBetween(precioMin, precioMax))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorPrecioBetween(precioMin, precioMax);
        
        assertNotNull(productos);
        assertEquals(0, productos.size());
        verify(productoRepository, times(1)).findByPrecioBetween(precioMin, precioMax);
    }

    @Test   
    public void testObtenerProductosPorCalificacionBetween_ListaVacia(){
        Integer puntajeMin = 3;
        Integer puntajeMax = 5;
        List<Producto> productosEsperados = new ArrayList<>();
        
        when(productoRepository.findByCalificacionBetween(puntajeMin, puntajeMax))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorCalificacionBetween(puntajeMin, puntajeMax);
        
        assertNotNull(productos);
        assertEquals(0, productos.size());
        verify(productoRepository, times(1)).findByCalificacionBetween(puntajeMin, puntajeMax);
    }

    @Test   
    public void testObtenerProductosPorSedeId_ListaVacia(){
        Integer sedeId = 1;
        List<Producto> productosEsperados = new ArrayList<>();
        
        when(productoRepository.findBySedeId(sedeId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorSedeId(sedeId);
        
        assertNotNull(productos);
        assertEquals(0, productos.size());
        verify(productoRepository, times(1)).findBySedeId(sedeId);
    }

    @Test   
    public void testObtenerProductosPorMarcaIdAndCategoriaIdAndSedeId_ListaVacia(){
        Integer marcaId = 1;
        Integer categoriaId = 2;
        Integer sedeId = 3;
        List<Producto> productosEsperados = new ArrayList<>();
        
        when(productoRepository.findByMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId);
        
        assertNotNull(productos);
        assertEquals(0, productos.size());
        verify(productoRepository, times(1)).findByMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId);
    }

    @Test   
    public void testObtenerProductosPorSedeIdAndCategoriaId_ListaVacia(){
        Integer sedeId = 1;
        Integer categoriaId = 2;
        List<Producto> productosEsperados = new ArrayList<>();
        
        when(productoRepository.findBySedeIdAndCategoriaId(sedeId, categoriaId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorSedeIdAndCategoriaId(sedeId, categoriaId);
        
        assertNotNull(productos);
        assertEquals(0, productos.size());
        verify(productoRepository, times(1)).findBySedeIdAndCategoriaId(sedeId, categoriaId);
    }

    @Test   
    public void testObtenerProductosPorMarcaIdAndSedeId_ListaVacia(){
        Integer marcaId = 1;
        Integer sedeId = 2;
        List<Producto> productosEsperados = new ArrayList<>();
        
        when(productoRepository.findByMarcaIdAndSedeId(marcaId, sedeId))
                .thenReturn(productosEsperados);
        
        List<Producto> productos = productoService.obtenerProductosPorMarcaIdAndSedeId(marcaId, sedeId);
        
        assertNotNull(productos);
        assertEquals(0, productos.size());
        verify(productoRepository, times(1)).findByMarcaIdAndSedeId(marcaId, sedeId);
    }
}
