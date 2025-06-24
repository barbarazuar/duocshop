package com.duocshop.duocshop.assemblers;

import com.duocshop.duocshop.controller.v2.CategoriaControllerV2;
import com.duocshop.duocshop.model.Categoria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>> {
    @Override
    public EntityModel<Categoria> toModel(Categoria categoria) {
        return EntityModel.of(categoria,
                linkTo(methodOn(CategoriaControllerV2.class).getCategoriaById(categoria.getId())).withSelfRel(),
                linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withRel("categorias"),
                linkTo(methodOn(CategoriaControllerV2.class).createCategoria(categoria)).withRel("crear"),
                linkTo(methodOn(CategoriaControllerV2.class).updateCategoria(categoria.getId(), categoria)).withRel("actualizar"),
                linkTo(methodOn(CategoriaControllerV2.class).deleteCategoria(categoria.getId())).withRel("eliminar"),
                linkTo(methodOn(CategoriaControllerV2.class).patchCategoria(categoria.getId(), categoria)).withRel("patch")
        );
    }
} 