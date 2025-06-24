package com.duocshop.duocshop.assemblers;

import com.duocshop.duocshop.controller.v2.SedeControllerV2;
import com.duocshop.duocshop.model.Sede;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SedeModelAssembler implements RepresentationModelAssembler<Sede, EntityModel<Sede>> {
    @Override
    public EntityModel<Sede> toModel(Sede sede) {
        return EntityModel.of(sede,
                linkTo(methodOn(SedeControllerV2.class).getSedeById(sede.getId())).withSelfRel(),
                linkTo(methodOn(SedeControllerV2.class).getAllSedes()).withRel("sedes"),
                linkTo(methodOn(SedeControllerV2.class).createSede(sede)).withRel("crear"),
                linkTo(methodOn(SedeControllerV2.class).updateSede(sede.getId(), sede)).withRel("actualizar"),
                linkTo(methodOn(SedeControllerV2.class).deleteSede(sede.getId())).withRel("eliminar"),
                linkTo(methodOn(SedeControllerV2.class).patchSede(sede.getId(), sede)).withRel("patch")
        );
    }
} 