package com.duocshop.duocshop.controller.v2;

import com.duocshop.duocshop.assemblers.UsuarioCalificacionModelAssembler;
import com.duocshop.duocshop.model.UsuarioCalificacion;
import com.duocshop.duocshop.service.UsuarioCalificacionService;
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
@RequestMapping("/api/v2/usuarios-calificaciones")
public class UsuarioCalificacionControllerV2 {

    @Autowired
    private UsuarioCalificacionService usuarioCalificacionService;

    @Autowired
    private UsuarioCalificacionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<UsuarioCalificacion>>> getAllUsuarioCalificaciones() {
        List<EntityModel<UsuarioCalificacion>> usuarioCalificaciones = usuarioCalificacionService.obtenerUsuarioCalificaciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (usuarioCalificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                usuarioCalificaciones,
                linkTo(methodOn(UsuarioCalificacionControllerV2.class).getAllUsuarioCalificaciones()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioCalificacion>> getUsuarioCalificacionById(@PathVariable Integer id) {
        try {
            UsuarioCalificacion usuarioCalificacion = usuarioCalificacionService.obtenerUsuarioCalificacionPorId(id);
            return ResponseEntity.ok(assembler.toModel(usuarioCalificacion));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioCalificacion>> createUsuarioCalificacion(@RequestBody UsuarioCalificacion usuarioCalificacion) {
        UsuarioCalificacion newUsuarioCalificacion = usuarioCalificacionService.guardarUsuarioCalificacion(usuarioCalificacion);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioCalificacionControllerV2.class).getUsuarioCalificacionById(newUsuarioCalificacion.getId())).toUri())
                .body(assembler.toModel(newUsuarioCalificacion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioCalificacion>> updateUsuarioCalificacion(@PathVariable Integer id, @RequestBody UsuarioCalificacion usuarioCalificacion) {
        UsuarioCalificacion updatedUsuarioCalificacion = usuarioCalificacionService.actualizarUsuarioCalificacion(id, usuarioCalificacion);
        return ResponseEntity.ok(assembler.toModel(updatedUsuarioCalificacion));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioCalificacion>> patchUsuarioCalificacion(@PathVariable Integer id, @RequestBody UsuarioCalificacion usuarioCalificacion) {
        UsuarioCalificacion updatedUsuarioCalificacion = usuarioCalificacionService.actualizarUsuarioCalificacion(id, usuarioCalificacion);
        return ResponseEntity.ok(assembler.toModel(updatedUsuarioCalificacion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteUsuarioCalificacion(@PathVariable Integer id) {
        try {
            usuarioCalificacionService.eliminarUsuarioCalificacion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

} 