package com.duocshop.duocshop.controller.v2;

import com.duocshop.duocshop.assemblers.MarcaModelAssembler;
import com.duocshop.duocshop.model.Marca;
import com.duocshop.duocshop.service.MarcaService;
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
@RequestMapping("/api/v2/marcas")
public class MarcaControllerV2 {

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private MarcaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Marca>>> getAllMarcas() {
        List<EntityModel<Marca>> marcas = marcaService.obtenerMarcas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                marcas,
                linkTo(methodOn(MarcaControllerV2.class).getAllMarcas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marca>> getMarcaById(@PathVariable Integer id) {
        try {
            Marca marca = marcaService.obtenerMarcaPorId(id);
            return ResponseEntity.ok(assembler.toModel(marca));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marca>> createMarca(@RequestBody Marca marca) {
        Marca newMarca = marcaService.guardarMarca(marca);
        return ResponseEntity
                .created(linkTo(methodOn(MarcaControllerV2.class).getMarcaById(newMarca.getId())).toUri())
                .body(assembler.toModel(newMarca));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marca>> updateMarca(@PathVariable Integer id, @RequestBody Marca marca) {
        Marca updatedMarca = marcaService.actualizarMarca(id, marca);
        return ResponseEntity.ok(assembler.toModel(updatedMarca));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Marca>> patchMarca(@PathVariable Integer id, @RequestBody Marca marca) {
        Marca updatedMarca = marcaService.actualizarMarca(id, marca);
        return ResponseEntity.ok(assembler.toModel(updatedMarca));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMarca(@PathVariable Integer id) {
        try {
            marcaService.eliminarMarca(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 