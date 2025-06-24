package com.duocshop.duocshop.controller.v2;

import com.duocshop.duocshop.assemblers.CategoriaModelAssembler;
import com.duocshop.duocshop.model.Categoria;
import com.duocshop.duocshop.service.CategoriaService;
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
@RequestMapping("/api/v2/categorias")
public class CategoriaControllerV2 {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Categoria>>> getAllCategorias() {
        List<EntityModel<Categoria>> categorias = categoriaService.obtenerCategorias().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                categorias,
                linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Categoria>> getCategoriaById(@PathVariable Integer id) {
        try {
            Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
            return ResponseEntity.ok(assembler.toModel(categoria));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Categoria>> createCategoria(@RequestBody Categoria categoria) {
        Categoria newCategoria = categoriaService.guardarCategoria(categoria);
        return ResponseEntity
                .created(linkTo(methodOn(CategoriaControllerV2.class).getCategoriaById(newCategoria.getId())).toUri())
                .body(assembler.toModel(newCategoria));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Categoria>> updateCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        Categoria updatedCategoria = categoriaService.actualizarCategoria(id, categoria);
        return ResponseEntity.ok(assembler.toModel(updatedCategoria));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Categoria>> patchCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        Categoria updatedCategoria = categoriaService.actualizarCategoria(id, categoria);
        return ResponseEntity.ok(assembler.toModel(updatedCategoria));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
        try {
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 