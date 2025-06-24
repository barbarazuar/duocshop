package com.duocshop.duocshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocshop.duocshop.model.Categoria;
import com.duocshop.duocshop.repository.CategoriaRepository;
import com.duocshop.duocshop.repository.CategoriaProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    public List<Categoria> obtenerCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Integer id, Categoria categoria) {
        Categoria existente = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        if (categoria.getNombre() != null) {
            existente.setNombre(categoria.getNombre());
        }
        if (categoria.getCategoriaProductos() != null) {
            existente.setCategoriaProductos(categoria.getCategoriaProductos());
        }
        return categoriaRepository.save(existente);
    }

    public void eliminarCategoria(Integer id) {
        // verifica si la categoria existe
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        // elimina las relaciones CategoriaProducto asociadas a la categoria
        categoriaProductoRepository.deleteByCategoria(categoria);

        // elimina la categoria
        categoriaRepository.delete(categoria);
    }   
    
    public Categoria obtenerCategoriaPorId(Integer id) {
        return categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }
}
