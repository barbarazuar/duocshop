package com.duocshop.duocshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.duocshop.duocshop.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Buscar productos por marca
    @Query("SELECT p FROM Producto p WHERE p.marca.id = :marcaId")
    List<Producto> findByMarcaId(@Param("marcaId") Integer marcaId);

    // Buscar productos por categoría
    @Query("SELECT p FROM Producto p JOIN p.categoriaProductos cp WHERE cp.categoria.id = :categoriaId")
    List<Producto> findByCategoriaId(@Param("categoriaId") Integer categoriaId);

    // Buscar productos por rango de precios
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioBetween(@Param("precioMin") Integer precioMin,
            @Param("precioMax") Integer precioMax);

    // Buscar productos por rango de calificación
    @Query("SELECT DISTINCT p FROM Producto p JOIN p.usuarioProductos up JOIN up.usuario u JOIN UsuarioCalificacion uc ON uc.usuario = u JOIN uc.calificacion c WHERE c.puntaje BETWEEN :puntajeMin AND :puntajeMax")
    List<Producto> findByCalificacionBetween(@Param("puntajeMin") Integer puntajeMin,
            @Param("puntajeMax") Integer puntajeMax);

    // Buscar productos por sede
    @Query("SELECT DISTINCT p FROM Producto p JOIN p.usuarioProductos up JOIN up.usuario u WHERE u.sede.id = :sedeId")
    List<Producto> findBySedeId(@Param("sedeId") Integer sedeId);

    // Buscar productos por marca, categoría y sede
    @Query("SELECT DISTINCT p FROM Producto p JOIN p.usuarioProductos up JOIN up.usuario u JOIN p.categoriaProductos cp WHERE p.marca.id = :marcaId AND cp.categoria.id = :categoriaId AND u.sede.id = :sedeId")
    List<Producto> findByMarcaIdAndCategoriaIdAndSedeId(@Param("marcaId") Integer marcaId,
            @Param("categoriaId") Integer categoriaId,
            @Param("sedeId") Integer sedeId);

    // Buscar productos por sede y categoría
    @Query("SELECT DISTINCT p FROM Producto p JOIN p.usuarioProductos up JOIN up.usuario u JOIN p.categoriaProductos cp WHERE u.sede.id = :sedeId AND cp.categoria.id = :categoriaId")
    List<Producto> findBySedeIdAndCategoriaId(@Param("sedeId") Integer sedeId,
            @Param("categoriaId") Integer categoriaId);

    // Buscar productos por marca y sede
    @Query("SELECT DISTINCT p FROM Producto p JOIN p.usuarioProductos up JOIN up.usuario u WHERE p.marca.id = :marcaId AND u.sede.id = :sedeId")
    List<Producto> findByMarcaIdAndSedeId(@Param("marcaId") Integer marcaId,
            @Param("sedeId") Integer sedeId);

}
