package com.duocshop.duocshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.duocshop.duocshop.model.Calificacion;
import com.duocshop.duocshop.service.CalificacionService;

@Tag(name = "Calificacion", description = "Operaciones relacionadas con calificaciones")
@RestController
@RequestMapping("/api/v1/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @Operation(summary = "Listar calificaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de calificaciones obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay calificaciones disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Calificacion>> listar() {
        List<Calificacion> calificaciones = calificacionService.obtenerCalificaciones();
        if (calificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(calificaciones);
    }

    @Operation(summary = "Obtener una calificación por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Calificación encontrada"),
        @ApiResponse(responseCode = "404", description = "Calificación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> obtenerPorId(@PathVariable Integer id) {
        Calificacion calificacion = calificacionService.obtenerCalificacionPorId(id);
        if (calificacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(calificacion);
    }

    @Operation(summary = "Guardar una nueva calificación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Calificación creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Calificacion> guardar(@RequestBody Calificacion calificacion) {
        Calificacion nuevaCalificacion = calificacionService.guardarCalificacion(calificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCalificacion);
    }

    @Operation(summary = "Actualizar una calificación por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Calificación actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Calificación no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Calificacion> actualizar(@PathVariable Integer id, @RequestBody Calificacion calificacion) {
        try {
            calificacion.setId(id);
            Calificacion actualizada = calificacionService.actualizarCalificacion(id, calificacion);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar parcialmente una calificación por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Calificación actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Calificación no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Calificacion> patchCalificacion(@PathVariable Integer id, @RequestBody Calificacion calificacion) {
        try {
            calificacion.setId(id);
            Calificacion calificacionActualizada = calificacionService.actualizarCalificacion(id, calificacion);
            return ResponseEntity.ok(calificacionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try{
            calificacionService.eliminarCalificacion(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
