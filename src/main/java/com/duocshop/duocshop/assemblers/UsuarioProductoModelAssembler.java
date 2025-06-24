package com.duocshop.duocshop.assemblers;

import com.duocshop.duocshop.controller.v2.UsuarioProductoControllerV2;
import com.duocshop.duocshop.model.UsuarioProducto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioProductoModelAssembler implements RepresentationModelAssembler<UsuarioProducto, EntityModel<UsuarioProducto>> {
    @Override
    public EntityModel<UsuarioProducto> toModel(UsuarioProducto usuarioProducto) {
        return EntityModel.of(usuarioProducto,
                linkTo(methodOn(UsuarioProductoControllerV2.class).getUsuarioProductoById(usuarioProducto.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioProductoControllerV2.class).getAllUsuarioProductos()).withRel("self-collection"),
                linkTo(methodOn(UsuarioProductoControllerV2.class).createUsuarioProducto(usuarioProducto)).withRel("crear"),
                linkTo(methodOn(UsuarioProductoControllerV2.class).updateUsuarioProducto(usuarioProducto.getId(), usuarioProducto)).withRel("actualizar"),
                linkTo(methodOn(UsuarioProductoControllerV2.class).deleteUsuarioProducto(usuarioProducto.getId())).withRel("eliminar"),
                linkTo(methodOn(UsuarioProductoControllerV2.class).patchUsuarioProducto(usuarioProducto.getId(), usuarioProducto)).withRel("patch")
        );
    }
} 