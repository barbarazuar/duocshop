package com.duocshop.duocshop.controller.v2;

import com.duocshop.duocshop.assemblers.CategoriaProductoModelAssembler;
import com.duocshop.duocshop.model.CategoriaProducto;
import com.duocshop.duocshop.service.CategoriaProductoService;
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
@RequestMapping("/api/v2/categorias-productos")
public class CategoriaProductoControllerV2 {

    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @Autowired
    private CategoriaProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<CategoriaProducto>>> getAllCategoriaProductos() {
        List<EntityModel<CategoriaProducto>> categoriaProductos = categoriaProductoService.obtenerCategoriaProductos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (categoriaProductos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                categoriaProductos,
                linkTo(methodOn(CategoriaProductoControllerV2.class).getAllCategoriaProductos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaProducto>> getCategoriaProductoById(@PathVariable Integer id) {
        try {
            CategoriaProducto categoriaProducto = categoriaProductoService.obtenerCategoriaProductoPorId(id);
            return ResponseEntity.ok(assembler.toModel(categoriaProducto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaProducto>> createCategoriaProducto(@RequestBody CategoriaProducto categoriaProducto) {
        CategoriaProducto newCategoriaProducto = categoriaProductoService.guardarCategoriaProducto(categoriaProducto);
        return ResponseEntity
                .created(linkTo(methodOn(CategoriaProductoControllerV2.class).getCategoriaProductoById(newCategoriaProducto.getId())).toUri())
                .body(assembler.toModel(newCategoriaProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaProducto>> updateCategoriaProducto(@PathVariable Integer id, @RequestBody CategoriaProducto categoriaProducto) {
        CategoriaProducto updatedCategoriaProducto = categoriaProductoService.actualizarCategoriaProducto(id, categoriaProducto);
        return ResponseEntity.ok(assembler.toModel(updatedCategoriaProducto));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaProducto>> patchCategoriaProducto(@PathVariable Integer id, @RequestBody CategoriaProducto categoriaProducto) {
        CategoriaProducto updatedCategoriaProducto = categoriaProductoService.actualizarCategoriaProducto(id, categoriaProducto);
        return ResponseEntity.ok(assembler.toModel(updatedCategoriaProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteCategoriaProducto(@PathVariable Integer id) {
        try {
            categoriaProductoService.eliminarCategoriaProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 