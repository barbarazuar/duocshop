package com.duocshop.duocshop.assemblers;

import com.duocshop.duocshop.controller.v2.MarcaControllerV2;
import com.duocshop.duocshop.model.Marca;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MarcaModelAssembler implements RepresentationModelAssembler<Marca, EntityModel<Marca>> {
    @Override
    public EntityModel<Marca> toModel(Marca marca) {
        return EntityModel.of(marca,
                linkTo(methodOn(MarcaControllerV2.class).getMarcaById(marca.getId())).withSelfRel(),
                linkTo(methodOn(MarcaControllerV2.class).getAllMarcas()).withRel("marcas"),
                linkTo(methodOn(MarcaControllerV2.class).createMarca(marca)).withRel("crear"),
                linkTo(methodOn(MarcaControllerV2.class).updateMarca(marca.getId(), marca)).withRel("actualizar"),
                linkTo(methodOn(MarcaControllerV2.class).deleteMarca(marca.getId())).withRel("eliminar"),
                linkTo(methodOn(MarcaControllerV2.class).patchMarca(marca.getId(), marca)).withRel("patch")
        );
    }
} 