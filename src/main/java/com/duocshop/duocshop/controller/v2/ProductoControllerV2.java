package com.duocshop.duocshop.controller.v2;

import com.duocshop.duocshop.assemblers.ProductoModelAssembler;
import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.obtenerProductos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Integer id) {
        try {
            Producto producto = productoService.obtenerProductoPorId(id);
            return ResponseEntity.ok(assembler.toModel(producto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.guardarProducto(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(newProducto.getId())).toUri())
                .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto updatedProducto = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> patchProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto updatedProducto = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/marca/{marcaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosByMarcaId(@PathVariable Integer marcaId) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductosPorMarcaId(marcaId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByMarcaId(marcaId)).withSelfRel()
        ));
    }

    @GetMapping(value = "/categoria/{categoriaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosByCategoriaId(@PathVariable Integer categoriaId) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductosPorCategoriaId(categoriaId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByCategoriaId(categoriaId)).withSelfRel()
        ));
    }

    // Endpoints para búsquedas avanzadas con HATEOAS
    @GetMapping(value = "/precio/{precioMin}/{precioMax}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosByPrecioBetween(
            @PathVariable Integer precioMin, 
            @PathVariable Integer precioMax) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductosPorPrecioBetween(precioMin, precioMax).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByPrecioBetween(precioMin, precioMax)).withSelfRel()
        ));
    }
    
    @GetMapping(value = "/calificacion/{puntajeMin}/{puntajeMax}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosByCalificacionBetween(
            @PathVariable Integer puntajeMin, 
            @PathVariable Integer puntajeMax) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductosPorCalificacionBetween(puntajeMin, puntajeMax).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByCalificacionBetween(puntajeMin, puntajeMax)).withSelfRel()
        ));
    }
    
    @GetMapping(value = "/sede/{sedeId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosBySedeId(@PathVariable Integer sedeId) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductosPorSedeId(sedeId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosBySedeId(sedeId)).withSelfRel()
        ));
    }
    
    @GetMapping(value = "/marca/{marcaId}/categoria/{categoriaId}/sede/{sedeId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosByMarcaIdAndCategoriaIdAndSedeId(
            @PathVariable Integer marcaId, 
            @PathVariable Integer categoriaId, 
            @PathVariable Integer sedeId) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductosPorMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByMarcaIdAndCategoriaIdAndSedeId(marcaId, categoriaId, sedeId)).withSelfRel()
        ));
    }
    
    @GetMapping(value = "/sede/{sedeId}/categoria/{categoriaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosBySedeIdAndCategoriaId(
            @PathVariable Integer sedeId, 
            @PathVariable Integer categoriaId) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductosPorSedeIdAndCategoriaId(sedeId, categoriaId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosBySedeIdAndCategoriaId(sedeId, categoriaId)).withSelfRel()
        ));
    }
    
    @GetMapping(value = "/marca/{marcaId}/sede/{sedeId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosByMarcaIdAndSedeId(
            @PathVariable Integer marcaId, 
            @PathVariable Integer sedeId) {
        List<EntityModel<Producto>> productos = productoService.obtenerProductosPorMarcaIdAndSedeId(marcaId, sedeId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByMarcaIdAndSedeId(marcaId, sedeId)).withSelfRel()
        ));
    }
} 