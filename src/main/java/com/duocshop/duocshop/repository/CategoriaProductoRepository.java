package com.duocshop.duocshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duocshop.duocshop.model.Categoria;
import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.model.CategoriaProducto;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Integer> {
    
    // Eliminar por categoria
    void deleteByCategoria(Categoria categoria);
    
    // Eliminar por producto
    void deleteByProducto(Producto producto);
    
    // Buscar por categoria
    List<CategoriaProducto> findByCategoria(Categoria categoria);
    
    // Buscar por producto
    List<CategoriaProducto> findByProducto(Producto producto);

}
