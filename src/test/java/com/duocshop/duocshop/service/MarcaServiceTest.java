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

import com.duocshop.duocshop.model.Marca;
import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.repository.MarcaRepository;

@SpringBootTest
public class MarcaServiceTest {

    @Autowired
    private MarcaService marcaService;

    @MockBean
    private MarcaRepository marcaRepository;

    private Marca crearMarca(){
        return new Marca(
            1,
            "MarcaTest",
            new ArrayList<Producto>()
        );
    }

    @Test
    public void testObtenerMarcas(){
        when(marcaRepository.findAll()).
        thenReturn(List.of(crearMarca()));

        List<Marca> marcas = marcaService.obtenerMarcas();
        assertNotNull(marcas);
        assertEquals(1, marcas.size());
    }

    @Test
    public void testObtenerMarcaPorId(){
        when(marcaRepository.findById(1))
        .thenReturn(Optional.of(crearMarca()));

        Marca marca = marcaService.obtenerMarcaPorId(1);
        assertNotNull(marca);
        assertEquals("MarcaTest", marca.getNombre());
    }

    @Test
    public void testGuardarMarca(){
        Marca marca = crearMarca();
        when(marcaRepository.save(marca))
        .thenReturn(marca);

        Marca marcaGuardada = marcaService.guardarMarca(marca);
        assertNotNull(marcaGuardada);
        assertEquals("MarcaTest", marcaGuardada.getNombre());
    }

    @Test
    public void testActualizarMarca(){
        Marca existingMarca = crearMarca();
        Marca patchData = new Marca();
        patchData.setId(1);
        patchData.setNombre("MarcaActualizada");
        when(marcaRepository.findById(1))
        .thenReturn(Optional.of(existingMarca));
        when(marcaRepository.save(any(Marca.class)))
        .thenReturn(patchData);
        Marca patchedMarca = marcaService.actualizarMarca(1, patchData);
        assertNotNull(patchedMarca);
        assertEquals("MarcaActualizada", patchedMarca.getNombre());
    }

    @Test
    public void testEliminarMarca(){
        Marca marca = crearMarca();
        when(marcaRepository.findById(1))
        .thenReturn(Optional.of(marca));
        doNothing().when(marcaRepository).delete(marca);
        
        marcaService.eliminarMarca(1);
        
        verify(marcaRepository, times(1)).findById(1);
        verify(marcaRepository, times(1)).delete(marca);
    }

    @Test
    public void testEliminarMarca_NoEncontrado() {
        when(marcaRepository.findById(999))
                .thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            marcaService.eliminarMarca(999);
        });
        
        verify(marcaRepository, times(1)).findById(999);
        verify(marcaRepository, never()).delete(any());
    }
    
}
