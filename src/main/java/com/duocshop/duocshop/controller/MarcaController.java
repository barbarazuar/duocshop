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

import com.duocshop.duocshop.model.Marca;
import com.duocshop.duocshop.service.MarcaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Marca", description = "Operaciones relacionadas con marcas")
@RestController
@RequestMapping("/api/v1/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @Operation(summary = "Listar marcas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de marcas obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay marcas disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Marca>> listar() {
        List<Marca> marcas = marcaService.obtenerMarcas();
        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(marcas);
    }

    @Operation(summary = "Obtener una marca por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marca encontrada"),
        @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerPorId(@PathVariable Integer id) {
        try {
            Marca marca = marcaService.obtenerMarcaPorId(id);
            return ResponseEntity.ok(marca);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Guardar una nueva marca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Marca creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Marca> guardar(@RequestBody Marca marca) {
        Marca nuevaMarca = marcaService.guardarMarca(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMarca);
    }

    @Operation(summary = "Actualizar una marca por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marca actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Marca no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizar(@PathVariable Integer id, @RequestBody Marca marca) {
        try {
            Marca actualizada = marcaService.actualizarMarca(id, marca);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar parcialmente una marca por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marca actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Marca no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Marca> patchMarca(@PathVariable Integer id, @RequestBody Marca marca) {
        try {
            Marca actualizada = marcaService.actualizarMarca(id, marca);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            marcaService.eliminarMarca(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 