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

import com.duocshop.duocshop.model.Sede;
import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.repository.SedeRepository;

@SpringBootTest
public class SedeServiceTest {

    @Autowired
    private SedeService sedeService;

    @MockBean
    private SedeRepository sedeRepository;

    private Sede crearSede() {
        return new Sede(
                1,
                "SedeTest",
                "DireccionTest",
                new ArrayList<Usuario>());
    }

    @Test
    public void testObtenerSedes() {
        when(sedeRepository.findAll())
                .thenReturn(List.of(crearSede()));
        List<Sede> sedes = sedeService.obtenerSedes();
        assertNotNull(sedes);
        assertEquals(1, sedes.size());
    }

    @Test
    public void testObtenerSedePorId() {
        when(sedeRepository.findById(1))
                .thenReturn(Optional.of(crearSede()));
        Sede sede = sedeService.obtenerSedePorId(1);
        assertNotNull(sede);
        assertEquals("SedeTest", sede.getNombre());
    }

    @Test
    public void testGuardarSede() {
        Sede sede = crearSede();
        when(sedeRepository.save(sede))
                .thenReturn(sede);
        Sede sedeGuardada = sedeService.guardarSede(sede);
        assertNotNull(sedeGuardada);
        assertEquals("SedeTest", sedeGuardada.getNombre());
    }

    @Test
    public void testActualizarSede() {
        Sede existingSede = crearSede();
        Sede patchData = new Sede();
        patchData.setNombre("SedeActualizada");
        
        when(sedeRepository.findById(1))
                .thenReturn(Optional.of(existingSede));
        when(sedeRepository.save(any(Sede.class)))
                .thenReturn(existingSede);

        Sede patchedSede = sedeService.actualizarSede(1, patchData);
        assertNotNull(patchedSede);
        assertEquals("SedeActualizada", patchedSede.getNombre());
    }

    @Test
    public void testEliminarSede() {
        Sede sede = crearSede();
        when(sedeRepository.findById(1))
                .thenReturn(Optional.of(sede));
        doNothing().when(sedeRepository).delete(sede);
        
        sedeService.eliminarSede(1);
        
        verify(sedeRepository, times(1)).findById(1);
        verify(sedeRepository, times(1)).delete(sede);
    }

    @Test
    public void testEliminarSede_NoEncontrado() {
        when(sedeRepository.findById(999))
                .thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            sedeService.eliminarSede(999);
        });
        
        verify(sedeRepository, times(1)).findById(999);
        verify(sedeRepository, never()).delete(any());
    }
}
