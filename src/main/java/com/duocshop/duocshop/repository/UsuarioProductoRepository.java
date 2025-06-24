package com.duocshop.duocshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.model.UsuarioProducto;

@Repository
public interface UsuarioProductoRepository extends JpaRepository<UsuarioProducto, Integer> {
    
    @Query(""" 
        SELECT up.id, up.usuario.nombre, up.producto.nombre FROM UsuarioProducto up
        """)
    List<Object[]> findUsuarioProducto();
    
    // Eliminar por usuario
    void deleteByUsuario(Usuario usuario);
    
    // Eliminar por producto
    void deleteByProducto(Producto producto);
    
    // Buscar por usuario
    List<UsuarioProducto> findByUsuario(Usuario usuario);
    
    // Buscar por producto
    List<UsuarioProducto> findByProducto(Producto producto);
}
