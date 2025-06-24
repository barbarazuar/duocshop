package com.duocshop.duocshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocshop.duocshop.model.Calificacion;
import com.duocshop.duocshop.repository.CalificacionRepository;
import com.duocshop.duocshop.repository.UsuarioCalificacionRepository;

import jakarta.transaction.Transactional;




@Service
@Transactional
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;
    
    @Autowired
    private UsuarioCalificacionRepository usuarioCalificacionRepository;
    


    public List<Calificacion> obtenerCalificaciones() {
        return calificacionRepository.findAll();
    }

    public Calificacion guardarCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    public Calificacion actualizarCalificacion(Integer id, Calificacion calificacion) {
        Calificacion existente = calificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Calificacion no encontrada"));

        if (calificacion.getPuntaje() != 0) {
            existente.setPuntaje(calificacion.getPuntaje());
        }
        if (calificacion.getComentario() != null) {
            existente.setComentario(calificacion.getComentario());
        }
        if (calificacion.getUsuarioCalificaciones() != null) {
            existente.setUsuarioCalificaciones(calificacion.getUsuarioCalificaciones());
        }
        return calificacionRepository.save(existente);
    }

    public void eliminarCalificacion(Integer id) {
        // verifica si la calificacion existe
        Calificacion calificacion = calificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Calificacion no encontrada"));

        // elimina las relaciones UsuarioCalificacion asociadas a la calificacion
        usuarioCalificacionRepository.deleteByCalificacion(calificacion);

        // elimina la calificacion
        calificacionRepository.delete(calificacion);
    }

    public Calificacion obtenerCalificacionPorId(Integer id) {
        return calificacionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Calificacion no encontrada"));
    }
}    