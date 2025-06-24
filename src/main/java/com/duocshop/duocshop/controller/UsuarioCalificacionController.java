package com.duocshop.duocshop.controller;

import java.util.List;
import java.util.Map;

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

import com.duocshop.duocshop.model.UsuarioCalificacion;
import com.duocshop.duocshop.service.UsuarioCalificacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "UsuarioCalificacion", description = "Operaciones relacionadas con las calificaciones de usuarios")
@RestController
@RequestMapping("/api/v1/usuarios-calificaciones")
public class UsuarioCalificacionController {

    @Autowired
    private UsuarioCalificacionService usuarioCalificacionService;

    @Operation(summary = "Listar calificaciones de usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuario-calificaciones obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay usuario-calificaciones disponibles")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioCalificacion>> listar() {
        List<UsuarioCalificacion> usuarioCalificaciones = usuarioCalificacionService.obtenerUsuarioCalificaciones();
        if (usuarioCalificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarioCalificaciones);
    }

    @Operation(summary = "Guardar una nueva calificación de usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario-calificación creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<UsuarioCalificacion> guardar(@RequestBody UsuarioCalificacion usuarioCalificacion) {
        UsuarioCalificacion nuevaUsuarioCalificacion = usuarioCalificacionService.guardarUsuarioCalificacion(usuarioCalificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUsuarioCalificacion);
    }

    @Operation(summary = "Actualizar una calificación de usuario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario-calificación actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario-calificación no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioCalificacion> actualizar(@PathVariable Integer id, @RequestBody UsuarioCalificacion usuarioCalificacion) {
        try {
            usuarioCalificacion.setId(id);
            UsuarioCalificacion actualizado = usuarioCalificacionService.actualizarUsuarioCalificacion(id, usuarioCalificacion);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar parcialmente una calificación de usuario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario-calificación actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario-calificación no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioCalificacion> patchUsuarioCalificacion(@PathVariable Integer id, @RequestBody UsuarioCalificacion usuarioCalificacion) {
        try {
            usuarioCalificacion.setId(id);
            UsuarioCalificacion updatedUsuarioCalificacion = usuarioCalificacionService.actualizarUsuarioCalificacion(id, usuarioCalificacion);
            return ResponseEntity.ok(updatedUsuarioCalificacion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            usuarioCalificacionService.eliminarUsuarioCalificacion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/resumen")
    public ResponseEntity<List<Map<String, Object>>> resumen() {
        List<Map<String, Object>> resumen = usuarioCalificacionService.obtenerUsuarioCalificacion();
        if (resumen.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCalificacion> getUsuarioCalificacionById(@PathVariable Integer id) {
        UsuarioCalificacion usuarioCalificacion = usuarioCalificacionService.obtenerUsuarioCalificacionPorId(id);
        if (usuarioCalificacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioCalificacion);
    }
}
