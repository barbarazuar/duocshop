package com.duocshop.duocshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocshop.duocshop.model.CategoriaProducto;
import com.duocshop.duocshop.repository.CategoriaProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaProductoService {

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    public List<CategoriaProducto> obtenerCategoriaProductos() {
        return categoriaProductoRepository.findAll();
    }

    public CategoriaProducto guardarCategoriaProducto(CategoriaProducto categoriaProducto) {
        return categoriaProductoRepository.save(categoriaProducto);
    }

    public CategoriaProducto actualizarCategoriaProducto(Integer id, CategoriaProducto categoriaProducto) {
        CategoriaProducto existente = categoriaProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("CategoriaProducto no encontrada"));

        if (categoriaProducto.getCategoria() != null) {
            existente.setCategoria(categoriaProducto.getCategoria());
        }
        if (categoriaProducto.getProducto() != null) {
            existente.setProducto(categoriaProducto.getProducto());
        }
        return categoriaProductoRepository.save(existente);
    }

    public void eliminarCategoriaProducto(Integer id) {
        
        CategoriaProducto categoriaProducto = categoriaProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("CategoriaProducto no encontrado"));
        categoriaProductoRepository.delete(categoriaProducto);
    }

    public CategoriaProducto obtenerCategoriaProductoPorId(Integer id) {
        return categoriaProductoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("CategoriaProducto no encontrada"));
    }

}
