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

import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.service.ProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Producto", description = "Operaciones relacionadas con productos")
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay productos disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.obtenerProductos();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Guardar un nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @Operation(summary = "Actualizar un producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Producto actualizado = productoService.actualizarProducto(id, producto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Actualizar parcialmente un producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> patchProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Producto actualizado = productoService.actualizarProducto(id, producto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Eliminar un producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoints para buscar por marca
    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<Producto>> obtenerPorMarcaId(@PathVariable Integer marcaId) {
        List<Producto> productos = productoService.obtenerProductosPorMarcaId(marcaId);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
    
    // Endpoints para buscar por categoría
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> obtenerPorCategoriaId(@PathVariable Integer categoriaId) {
        List<Producto> productos = productoService.obtenerProductosPorCategoriaId(categoriaId);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }
    
    // Endpoints para búsquedas avanzadas
    @Operation(summary = "Buscar productos por rango de precios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay productos en el rango especificado")
    })
    @GetMapping("/precio/{precioMin}/{precioMax}")
    public ResponseEntity<List<Producto>> obtenerPorPrecioBetween(
            @PathVariable Integer precioMin, 
            @PathVariable Integer precioMax) {
        List<Producto> productos = productoService.obtenerProductosPorPrecioBetween(precioMin, precioMax);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
    @Operation(summary = "Buscar productos por rango de calificación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay productos con la calificación especificada")
    })
    @GetMapping("/calificacion/{puntajeMin}/{puntajeMax}")
    public ResponseEntity<List<Producto>> obtenerPorCalificacionBetween(
            @PathVariable Integer puntajeMin, 
            @PathVariable Integer puntajeMax) {
        List<Producto> productos = productoService.obtenerProductosPorCalificacionBetween(puntajeMin, puntajeMax);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
    @Operation(summary = "Buscar productos por sede")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay productos en la sede especificada")
    })
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<Producto>> obtenerPorSedeId(@PathVariable Integer sedeId) {
        List<Producto> productos = productoService.obtenerProductosPorSedeId(sedeId);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
    @Operation(summary = "Buscar productos por marca, categoría y sede")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay productos con los criterios especificados")
    })
    @GetMapping("/marca/{marcaId}/categoria/{categoriaId}/sede/{sedeId}")
    public ResponseEntity<List<Producto>> obtenerPorMarcaIdAndCategoriaIdAndSedeId(
            @PathVariable Integer marcaId, 
            @PathVariable Integer categoriaId, 
            @PathVariable Integer sedeId) {
        List<Producto> productos = productoService.obtenerProductosPorMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
    @Operation(summary = "Buscar productos por sede y categoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay productos con los criterios especificados")
    })
    @GetMapping("/sede/{sedeId}/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> obtenerPorSedeIdAndCategoriaId(
            @PathVariable Integer sedeId, 
            @PathVariable Integer categoriaId) {
        List<Producto> productos = productoService.obtenerProductosPorSedeIdAndCategoriaId(sedeId, categoriaId);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
    @Operation(summary = "Buscar productos por marca y sede")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay productos con los criterios especificados")
    })
    @GetMapping("/marca/{marcaId}/sede/{sedeId}")
    public ResponseEntity<List<Producto>> obtenerPorMarcaIdAndSedeId(
            @PathVariable Integer marcaId, 
            @PathVariable Integer sedeId) {
        List<Producto> productos = productoService.obtenerProductosPorMarcaIdAndSedeId(marcaId, sedeId);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    
}
