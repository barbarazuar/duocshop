package com.duocshop.duocshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.duocshop.duocshop.model.Sede;


import com.duocshop.duocshop.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findBySede(Sede sede);

    void deleteBySede(Sede sede);

    // Buscar por correo
    Usuario findByCorreo(String correo);
    
    // Buscar por nombre
    List<Usuario> findByNombre(String nombre);
    
    // Buscar por apellido
    List<Usuario> findByApellido(String apellido);

}


