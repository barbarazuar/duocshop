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

import com.duocshop.duocshop.model.CategoriaProducto;
import com.duocshop.duocshop.service.CategoriaProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "CategoriaProducto", description = "Operaciones relacionadas con las categorías de productos")
@RestController
@RequestMapping("/api/v1/categorias-productos")
public class CategoriaProductoController {

    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @Operation(summary = "Listar categorías de productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorías de productos obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay categorías de productos disponibles")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> listar() {
        List<CategoriaProducto> categoriaProductos = categoriaProductoService.obtenerCategoriaProductos();
        if (categoriaProductos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoriaProductos);
    }

    @Operation(summary = "Guardar una nueva categoría de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría de producto creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<CategoriaProducto> guardar(@RequestBody CategoriaProducto categoriaProducto) {
        CategoriaProducto nuevaCategoriaProducto = categoriaProductoService.guardarCategoriaProducto(categoriaProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoriaProducto);
    }

    @Operation(summary = "Actualizar un categoría-producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría-producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría-producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProducto> actualizar(@PathVariable Integer id, @RequestBody CategoriaProducto categoriaProducto) {
        try {
            categoriaProducto.setId(id);
            CategoriaProducto actualizado = categoriaProductoService.actualizarCategoriaProducto(id, categoriaProducto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar parcialmente un categoría-producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría-producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría-producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CategoriaProducto> patchCategoriaProducto(@PathVariable Integer id, @RequestBody CategoriaProducto categoriaProducto) {
        try {
            categoriaProducto.setId(id);
            CategoriaProducto updatedCategoriaProducto = categoriaProductoService.actualizarCategoriaProducto(id, categoriaProducto);
            return ResponseEntity.ok(updatedCategoriaProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            categoriaProductoService.eliminarCategoriaProducto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProducto> obtenerPorId(@PathVariable Integer id) {
        CategoriaProducto categoriaProducto = categoriaProductoService.obtenerCategoriaProductoPorId(id);
        if (categoriaProducto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaProducto);
    }
}
