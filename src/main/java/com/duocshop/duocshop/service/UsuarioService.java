package com.duocshop.duocshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.repository.UsuarioRepository;
import com.duocshop.duocshop.repository.UsuarioProductoRepository;
import com.duocshop.duocshop.repository.UsuarioCalificacionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioProductoRepository usuarioProductoRepository;

    @Autowired
    private UsuarioCalificacionRepository usuarioCalificacionRepository;

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Integer id, Usuario usuario) {
        Usuario existente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getNombre() != null) {
            existente.setNombre(usuario.getNombre());
        }
        if (usuario.getApellido() != null) {
            existente.setApellido(usuario.getApellido());
        }
        if (usuario.getCorreo() != null) {
            existente.setCorreo(usuario.getCorreo());
        }
        if (usuario.getContrasena() != null) {
            existente.setContrasena(usuario.getContrasena());
        }
        if (usuario.getSede() != null) {
            existente.setSede(usuario.getSede());
        }
        return usuarioRepository.save(existente);
    }
    
    public void eliminarUsuario(Integer id) {
        // verifica si el usuario existe
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // elimina las relaciones UsuarioProducto asociadas al usuario
        usuarioProductoRepository.deleteByUsuario(usuario);

        // elimina las relaciones UsuarioCalificacion asociadas al usuario
        usuarioCalificacionRepository.deleteByUsuario(usuario);

        // elimina el usuario
        usuarioRepository.delete(usuario);
    }
}
