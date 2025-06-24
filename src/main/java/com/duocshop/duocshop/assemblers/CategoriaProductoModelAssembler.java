package com.duocshop.duocshop.assemblers;

import com.duocshop.duocshop.controller.v2.CategoriaProductoControllerV2;
import com.duocshop.duocshop.model.CategoriaProducto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoriaProductoModelAssembler implements RepresentationModelAssembler<CategoriaProducto, EntityModel<CategoriaProducto>> {
    @Override
    public EntityModel<CategoriaProducto> toModel(CategoriaProducto categoriaProducto) {
        return EntityModel.of(categoriaProducto,
                linkTo(methodOn(CategoriaProductoControllerV2.class).getCategoriaProductoById(categoriaProducto.getId())).withSelfRel(),
                linkTo(methodOn(CategoriaProductoControllerV2.class).getAllCategoriaProductos()).withRel("categoriaProductos"),
                linkTo(methodOn(CategoriaProductoControllerV2.class).createCategoriaProducto(categoriaProducto)).withRel("crear"),
                linkTo(methodOn(CategoriaProductoControllerV2.class).updateCategoriaProducto(categoriaProducto.getId(), categoriaProducto)).withRel("actualizar"),
                linkTo(methodOn(CategoriaProductoControllerV2.class).deleteCategoriaProducto(categoriaProducto.getId())).withRel("eliminar"),
                linkTo(methodOn(CategoriaProductoControllerV2.class).patchCategoriaProducto(categoriaProducto.getId(), categoriaProducto)).withRel("patch")
        );
    }
} 