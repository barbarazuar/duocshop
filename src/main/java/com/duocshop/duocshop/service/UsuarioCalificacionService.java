package com.duocshop.duocshop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocshop.duocshop.model.UsuarioCalificacion;
import com.duocshop.duocshop.repository.UsuarioCalificacionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class UsuarioCalificacionService {

    @Autowired
    private UsuarioCalificacionRepository usuarioCalificacionRepository;

    public List<UsuarioCalificacion> obtenerUsuarioCalificaciones() {
        return usuarioCalificacionRepository.findAll();
    }
    public UsuarioCalificacion guardarUsuarioCalificacion(UsuarioCalificacion usuarioCalificacion) {
        return usuarioCalificacionRepository.save(usuarioCalificacion);
    }
    public UsuarioCalificacion actualizarUsuarioCalificacion(Integer id, UsuarioCalificacion usuarioCalificacion) {
        UsuarioCalificacion existente = usuarioCalificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UsuarioCalificacion no encontrada"));

        if (usuarioCalificacion.getUsuario() != null) {
            existente.setUsuario(usuarioCalificacion.getUsuario());
        }
        if (usuarioCalificacion.getCalificacion() != null) {
            existente.setCalificacion(usuarioCalificacion.getCalificacion());
        }
        return usuarioCalificacionRepository.save(existente);
    }
    public void eliminarUsuarioCalificacion(Integer id) {

        UsuarioCalificacion usuarioCalificacion = usuarioCalificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UsuarioCalificacion no encontrado"));

        usuarioCalificacionRepository.delete(usuarioCalificacion);
    }
    public List<Map<String, Object>> obtenerUsuarioCalificacion() {
        List<Object[]> resultados = usuarioCalificacionRepository.findUsuarioCalificacion();
        List<Map<String, Object>> lista = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("usuarioCalificacion", fila[0]);
            datos.put("nombreUsuario", fila[1]);
            datos.put("calificacionUsuario", fila[2]);
            lista.add(datos);
        }

        return lista;
    }

    public UsuarioCalificacion obtenerUsuarioCalificacionPorId(Integer id) {
        return usuarioCalificacionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("UsuarioCalificacion no encontrada"));
    }
}
