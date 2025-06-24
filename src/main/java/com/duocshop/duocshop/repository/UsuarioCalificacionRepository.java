package com.duocshop.duocshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.model.Calificacion;
import com.duocshop.duocshop.model.UsuarioCalificacion;

@Repository
public interface UsuarioCalificacionRepository extends JpaRepository<UsuarioCalificacion, Integer> {

    @Query(""" 
        SELECT uc.id, uc.usuario.nombre, uc.calificacion.puntaje FROM UsuarioCalificacion uc
        """)
    List<Object[]> findUsuarioCalificacion();
    
    // Eliminar por usuario
    void deleteByUsuario(Usuario usuario);
    
    // Eliminar por calificacion
    void deleteByCalificacion(Calificacion calificacion);
    
    // Buscar por usuario
    List<UsuarioCalificacion> findByUsuario(Usuario usuario);
    
    // Buscar por calificacion
    List<UsuarioCalificacion> findByCalificacion(Calificacion calificacion);

}
