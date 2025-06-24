package com.duocshop.duocshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocshop.duocshop.model.Sede;
import com.duocshop.duocshop.repository.SedeRepository;
import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Sede> obtenerSedes() {
        return sedeRepository.findAll();
    }   

    public Sede guardarSede(Sede sede) {
        return sedeRepository.save(sede);
    }

    public Sede actualizarSede(Integer id, Sede sede) {
        Sede existente = sedeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        if (sede.getNombre() != null) {
            existente.setNombre(sede.getNombre());
        }
        if (sede.getDireccion() != null) {
            existente.setDireccion(sede.getDireccion());
        }
        if (sede.getUsuarios() != null) {
            existente.setUsuarios(sede.getUsuarios());
        }
        return sedeRepository.save(existente);
    }

    public void eliminarSede(Integer id) {
        // verifica si la sede existe
        Sede sede = sedeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        // listado de usuarios asociados a la sede
        List<Usuario> usuarios = usuarioRepository.findBySede(sede);

        // elimina los usuarios asociados a la sede, ejecutando el método eliminarUsuario de UsuarioService
        for (Usuario usuario : usuarios) {
            usuarioService.eliminarUsuario(usuario.getId());
        }

        // elimina la sede
        sedeRepository.delete(sede);
    }
    
    public Sede obtenerSedePorId(Integer id) {
        return sedeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
    }
}
