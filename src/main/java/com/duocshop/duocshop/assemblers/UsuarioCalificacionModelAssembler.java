package com.duocshop.duocshop.assemblers;

import com.duocshop.duocshop.controller.v2.UsuarioCalificacionControllerV2;
import com.duocshop.duocshop.model.UsuarioCalificacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioCalificacionModelAssembler implements RepresentationModelAssembler<UsuarioCalificacion, EntityModel<UsuarioCalificacion>> {
    @Override
    public EntityModel<UsuarioCalificacion> toModel(UsuarioCalificacion usuarioCalificacion) {
        return EntityModel.of(usuarioCalificacion,
                linkTo(methodOn(UsuarioCalificacionControllerV2.class).getUsuarioCalificacionById(usuarioCalificacion.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioCalificacionControllerV2.class).getAllUsuarioCalificaciones()).withRel("usuarioCalificaciones"),
                linkTo(methodOn(UsuarioCalificacionControllerV2.class).createUsuarioCalificacion(usuarioCalificacion)).withRel("crear"),
                linkTo(methodOn(UsuarioCalificacionControllerV2.class).updateUsuarioCalificacion(usuarioCalificacion.getId(), usuarioCalificacion)).withRel("actualizar"),
                linkTo(methodOn(UsuarioCalificacionControllerV2.class).deleteUsuarioCalificacion(usuarioCalificacion.getId())).withRel("eliminar"),
                linkTo(methodOn(UsuarioCalificacionControllerV2.class).patchUsuarioCalificacion(usuarioCalificacion.getId(), usuarioCalificacion)).withRel("patch")
        );
    }
} 