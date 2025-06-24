package com.duocshop.duocshop.controller.v2;

import com.duocshop.duocshop.assemblers.UsuarioProductoModelAssembler;
import com.duocshop.duocshop.model.UsuarioProducto;
import com.duocshop.duocshop.service.UsuarioProductoService;
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
@RequestMapping("/api/v2/usuarios-productos")
public class UsuarioProductoControllerV2 {

    @Autowired
    private UsuarioProductoService usuarioProductoService;

    @Autowired
    private UsuarioProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<UsuarioProducto>>> getAllUsuarioProductos() {
        List<EntityModel<UsuarioProducto>> usuarioProductos = usuarioProductoService.obtenerUsuarioProductos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (usuarioProductos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                usuarioProductos,
                linkTo(methodOn(UsuarioProductoControllerV2.class).getAllUsuarioProductos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioProducto>> getUsuarioProductoById(@PathVariable Integer id) {
        try {
            UsuarioProducto usuarioProducto = usuarioProductoService.obtenerUsuarioProductoPorId(id);
            return ResponseEntity.ok(assembler.toModel(usuarioProducto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioProducto>> createUsuarioProducto(@RequestBody UsuarioProducto usuarioProducto) {
        UsuarioProducto newUsuarioProducto = usuarioProductoService.guardarUsuarioProducto(usuarioProducto);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioProductoControllerV2.class).getUsuarioProductoById(newUsuarioProducto.getId())).toUri())
                .body(assembler.toModel(newUsuarioProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioProducto>> updateUsuarioProducto(@PathVariable Integer id, @RequestBody UsuarioProducto usuarioProducto) {
        UsuarioProducto updatedUsuarioProducto = usuarioProductoService.actualizarUsuarioProducto(id, usuarioProducto);
        return ResponseEntity.ok(assembler.toModel(updatedUsuarioProducto));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioProducto>> patchUsuarioProducto(@PathVariable Integer id, @RequestBody UsuarioProducto usuarioProducto) {
        UsuarioProducto updatedUsuarioProducto = usuarioProductoService.actualizarUsuarioProducto(id, usuarioProducto);
        return ResponseEntity.ok(assembler.toModel(updatedUsuarioProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteUsuarioProducto(@PathVariable Integer id) {
        try {
            usuarioProductoService.eliminarUsuarioProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 