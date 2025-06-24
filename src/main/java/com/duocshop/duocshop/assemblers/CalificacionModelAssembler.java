package com.duocshop.duocshop.assemblers;

import com.duocshop.duocshop.controller.v2.CalificacionControllerV2;
import com.duocshop.duocshop.model.Calificacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CalificacionModelAssembler implements RepresentationModelAssembler<Calificacion, EntityModel<Calificacion>> {

    @Override
    public EntityModel<Calificacion> toModel(Calificacion calificacion) {
        return EntityModel.of(calificacion,
                linkTo(methodOn(CalificacionControllerV2.class).getCalificacionById(calificacion.getId())).withSelfRel(),
                linkTo(methodOn(CalificacionControllerV2.class).getAllCalificaciones()).withRel("calificaciones"),
                linkTo(methodOn(CalificacionControllerV2.class).createCalificacion(calificacion)).withRel("crear"),
                linkTo(methodOn(CalificacionControllerV2.class).updateCalificacion(calificacion.getId(), calificacion)).withRel("actualizar"),
                linkTo(methodOn(CalificacionControllerV2.class).deleteCalificacion(calificacion.getId())).withRel("eliminar"),
                linkTo(methodOn(CalificacionControllerV2.class).patchCalificacion(calificacion.getId(), calificacion)).withRel("patch")
        );
    }
} 