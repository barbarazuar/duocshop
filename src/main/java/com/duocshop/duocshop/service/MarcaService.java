package com.duocshop.duocshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocshop.duocshop.model.Marca;
import com.duocshop.duocshop.repository.MarcaRepository;
import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    public List<Marca> obtenerMarcas() {
        return marcaRepository.findAll();
    }

    public Marca obtenerMarcaPorId(Integer id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id: " + id));
    }

    public Marca guardarMarca(Marca marca) {
        return marcaRepository.save(marca);
    }

    public Marca actualizarMarca(Integer id, Marca marca) {
        Marca existente = marcaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        if (marca.getNombre() != null) {
            existente.setNombre(marca.getNombre());
        }
        if (marca.getProductos() != null) {
            existente.setProductos(marca.getProductos());
        }
        return marcaRepository.save(existente);
    }

    public void eliminarMarca(Integer id) {
        // verifica si la marca existe
        Marca marca = marcaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        // listado de productos asociados a la marca
        List<Producto> productos = productoRepository.findByMarcaId(marca.getId());

        // Elimina los productos asociados a la marca, ejecutando el método eliminarProducto de ProductoService
        for (Producto producto : productos) {
            productoService.eliminarProducto(producto.getId());
        }

        // elimina la marca
        marcaRepository.delete(marca);
    }
} 