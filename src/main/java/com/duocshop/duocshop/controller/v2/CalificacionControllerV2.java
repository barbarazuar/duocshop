package com.duocshop.duocshop.controller.v2;

import com.duocshop.duocshop.assemblers.CalificacionModelAssembler;
import com.duocshop.duocshop.model.Calificacion;
import com.duocshop.duocshop.service.CalificacionService;
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
@RequestMapping("/api/v2/calificaciones")
public class CalificacionControllerV2 {

    @Autowired
    private CalificacionService calificacionService;

    @Autowired
    private CalificacionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Calificacion>>> getAllCalificaciones() {
        List<EntityModel<Calificacion>> calificaciones = calificacionService.obtenerCalificaciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (calificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                calificaciones,
                linkTo(methodOn(CalificacionControllerV2.class).getAllCalificaciones()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Calificacion>> getCalificacionById(@PathVariable Integer id) {
        try {
            Calificacion calificacion = calificacionService.obtenerCalificacionPorId(id);
            return ResponseEntity.ok(assembler.toModel(calificacion));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Calificacion>> createCalificacion(@RequestBody Calificacion calificacion) {
        Calificacion newCalificacion = calificacionService.guardarCalificacion(calificacion);
        return ResponseEntity
                .created(linkTo(methodOn(CalificacionControllerV2.class).getCalificacionById(newCalificacion.getId())).toUri())
                .body(assembler.toModel(newCalificacion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Calificacion>> updateCalificacion(@PathVariable Integer id, @RequestBody Calificacion calificacion) {
        Calificacion updatedCalificacion = calificacionService.actualizarCalificacion(id, calificacion);
        return ResponseEntity.ok(assembler.toModel(updatedCalificacion));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Calificacion>> patchCalificacion(@PathVariable Integer id, @RequestBody Calificacion calificacion) {
        Calificacion updatedCalificacion = calificacionService.actualizarCalificacion(id, calificacion);
        return ResponseEntity.ok(assembler.toModel(updatedCalificacion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteCalificacion(@PathVariable Integer id) {
        try {
            calificacionService.eliminarCalificacion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 