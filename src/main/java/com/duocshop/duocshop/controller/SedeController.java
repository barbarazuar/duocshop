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

import com.duocshop.duocshop.model.Sede;
import com.duocshop.duocshop.service.SedeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Sede", description = "Operaciones relacionadas con sedes")
@RestController
@RequestMapping("/api/v1/sedes")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    @Operation(summary = "Listar sedes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sedes obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay sedes disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Sede>> listar() {
        List<Sede> sedes = sedeService.obtenerSedes();
        if (sedes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sedes);
    }

    @Operation(summary = "Guardar una nueva sede")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sede creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Sede> guardar(@RequestBody Sede sede) {
        Sede nuevaSede = sedeService.guardarSede(sede);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSede);
    }

    @Operation(summary = "Actualizar una sede por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sede actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sede no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Sede> actualizar(@PathVariable Integer id, @RequestBody Sede sede) {
        try {
            Sede actualizada = sedeService.actualizarSede(id, sede);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Actualizar parcialmente una sede por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sede actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sede no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Sede> patchSede(@PathVariable Integer id, @RequestBody Sede sede) {
        try {
            Sede actualizada = sedeService.actualizarSede(id, sede);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una sede por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            sedeService.eliminarSede(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sede> obtenerPorId(@PathVariable Integer id) {
        Sede sede = sedeService.obtenerSedePorId(id);
        if (sede == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sede);
    }
}
