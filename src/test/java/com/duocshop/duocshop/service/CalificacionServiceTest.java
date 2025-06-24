package com.duocshop.duocshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.duocshop.duocshop.model.UsuarioCalificacion;
import com.duocshop.duocshop.model.Calificacion;
import com.duocshop.duocshop.repository.CalificacionRepository;
import com.duocshop.duocshop.repository.UsuarioCalificacionRepository;

@SpringBootTest
public class CalificacionServiceTest {

    @Autowired
    private CalificacionService calificacionService;

    @MockBean
    private CalificacionRepository calificacionRepository;

    @MockBean
    private UsuarioCalificacionRepository usuarioCalificacionRepository;

    private Calificacion crearCalificacion() {
        return new Calificacion(
                1,
                5,
                "Excelente producto",
                new ArrayList<UsuarioCalificacion>());
    }

    @Test
    public void testObtenerCalificaciones() {
        when(calificacionRepository.findAll())
                .thenReturn(List.of(crearCalificacion()));
        List<Calificacion> calificaciones = calificacionService.obtenerCalificaciones();
        assertNotNull(calificaciones);
        assertEquals(1, calificaciones.size());
    }

    @Test
    public void testObtenerCalificacionPorId(){
        when(calificacionRepository.findById(1))
        .thenReturn(Optional.of(crearCalificacion()));
        Calificacion calificacion = calificacionService.obtenerCalificacionPorId(1);
        assertNotNull(calificacion);
        assertEquals(5, calificacion.getPuntaje());
    }

    @Test
    public void testGuardarCalificacion(){
        Calificacion calificacion = crearCalificacion();
        when(calificacionRepository.save(calificacion))
        .thenReturn(calificacion);
        Calificacion calificacionGuardada = calificacionService.guardarCalificacion(calificacion);
        assertNotNull(calificacionGuardada);
        assertEquals(5, calificacionGuardada.getPuntaje());
    }

    @Test
    public void testActualizarCalificacion(){
        Calificacion existingCalificacion = crearCalificacion();
        Calificacion patchData = new Calificacion();
        patchData.setPuntaje(4);
        
        when(calificacionRepository.findById(1))
        .thenReturn(Optional.of(existingCalificacion));
        when(calificacionRepository.save(any(Calificacion.class)))
        .thenReturn(existingCalificacion);

        Calificacion patchedCalificacion = calificacionService.actualizarCalificacion(1, patchData);
        assertNotNull(patchedCalificacion);
        assertEquals(4, patchedCalificacion.getPuntaje());
    }

    @Test
    public void testEliminarCalificacion(){
        Calificacion calificacion = crearCalificacion();
        when(calificacionRepository.findById(1))
                .thenReturn(Optional.of(calificacion));
        doNothing().when(usuarioCalificacionRepository).deleteByCalificacion(calificacion);
        doNothing().when(calificacionRepository).delete(calificacion);
        
        calificacionService.eliminarCalificacion(1);
        
        verify(calificacionRepository, times(1)).findById(1);
        verify(usuarioCalificacionRepository, times(1)).deleteByCalificacion(calificacion);
        verify(calificacionRepository, times(1)).delete(calificacion);
    }

    @Test
    public void testEliminarCalificacion_NoEncontrado() {
        when(calificacionRepository.findById(999))
                .thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            calificacionService.eliminarCalificacion(999);
        });
        
        verify(calificacionRepository, times(1)).findById(999);
        verify(calificacionRepository, never()).delete(any());
    }
}
