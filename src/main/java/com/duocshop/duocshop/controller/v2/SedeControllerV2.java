package com.duocshop.duocshop.controller.v2;

import com.duocshop.duocshop.assemblers.SedeModelAssembler;
import com.duocshop.duocshop.model.Sede;
import com.duocshop.duocshop.service.SedeService;
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
@RequestMapping("/api/v2/sedes")
public class SedeControllerV2 {

    @Autowired
    private SedeService sedeService;

    @Autowired
    private SedeModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Sede>>> getAllSedes() {
        List<EntityModel<Sede>> sedes = sedeService.obtenerSedes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (sedes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                sedes,
                linkTo(methodOn(SedeControllerV2.class).getAllSedes()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sede>> getSedeById(@PathVariable Integer id) {
        try {
            Sede sede = sedeService.obtenerSedePorId(id);
            return ResponseEntity.ok(assembler.toModel(sede));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sede>> createSede(@RequestBody Sede sede) {
        Sede newSede = sedeService.guardarSede(sede);
        return ResponseEntity
                .created(linkTo(methodOn(SedeControllerV2.class).getSedeById(newSede.getId())).toUri())
                .body(assembler.toModel(newSede));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sede>> updateSede(@PathVariable Integer id, @RequestBody Sede sede) {
        Sede updatedSede = sedeService.actualizarSede(id, sede);
        return ResponseEntity.ok(assembler.toModel(updatedSede));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sede>> patchSede(@PathVariable Integer id, @RequestBody Sede sede) {
        Sede updatedSede = sedeService.actualizarSede(id, sede);
        return ResponseEntity.ok(assembler.toModel(updatedSede));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSede(@PathVariable Integer id) {
        try {
            sedeService.eliminarSede(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 