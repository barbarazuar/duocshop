package com.duocshop.duocshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.repository.ProductoRepository;
import com.duocshop.duocshop.repository.CategoriaProductoRepository;
import com.duocshop.duocshop.repository.UsuarioProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Autowired
    private UsuarioProductoRepository usuarioProductoRepository;

    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Integer id, Producto producto) {
        Producto existente = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getNombre() != null) {
            existente.setNombre(producto.getNombre());
        }
        if (producto.getDescripcion() != null) {
            existente.setDescripcion(producto.getDescripcion());
        }
        if (producto.getPrecio() != null) {
            existente.setPrecio(producto.getPrecio());
        }
        if (producto.getMarca() != null) {
            existente.setMarca(producto.getMarca());
        }
        if (producto.getCategoriaProductos() != null) {
            existente.setCategoriaProductos(producto.getCategoriaProductos());
        }
        if (producto.getUsuarioProductos() != null) {
            existente.setUsuarioProductos(producto.getUsuarioProductos());
        }
        return productoRepository.save(existente);
    }

    public void eliminarProducto(Integer id) {
        // verifica si el producto existe
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // elimina las relaciones CategoriaProducto asociadas al producto
        categoriaProductoRepository.deleteByProducto(producto);

        // elimina las relaciones UsuarioProducto asociadas al producto
        usuarioProductoRepository.deleteByProducto(producto);

        // elimina el producto
        productoRepository.delete(producto);
    }

    public List<Producto> obtenerProductosPorMarcaId(Integer marcaId) {
        return productoRepository.findByMarcaId(marcaId);
    }

    public List<Producto> obtenerProductosPorCategoriaId(Integer categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    public List<Producto> obtenerProductosPorPrecioBetween(Integer precioMin, Integer precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax);
    }

    public List<Producto> obtenerProductosPorCalificacionBetween(Integer puntajeMin, Integer puntajeMax) {
        return productoRepository.findByCalificacionBetween(puntajeMin, puntajeMax);
    }

    public List<Producto> obtenerProductosPorSedeId(Integer sedeId) {
        return productoRepository.findBySedeId(sedeId);
    }

    public List<Producto> obtenerProductosPorMarcaIdAndCategoriaIdAndSedeId(Integer marcaId, Integer categoriaId, Integer sedeId) {
        return productoRepository.findByMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId);
    }

    public List<Producto> obtenerProductosPorSedeIdAndCategoriaId(Integer sedeId, Integer categoriaId) {
        return productoRepository.findBySedeIdAndCategoriaId(sedeId, categoriaId);
    }

    public List<Producto> obtenerProductosPorMarcaIdAndSedeId(Integer marcaId, Integer sedeId) {
        return productoRepository.findByMarcaIdAndSedeId(marcaId, sedeId);
    }

}
