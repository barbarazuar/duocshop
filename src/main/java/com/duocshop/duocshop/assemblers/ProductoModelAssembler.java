package com.duocshop.duocshop.assemblers;

import com.duocshop.duocshop.controller.v2.ProductoControllerV2;
import com.duocshop.duocshop.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {
    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("productos"),
                linkTo(methodOn(ProductoControllerV2.class).createProducto(producto)).withRel("crear"),
                linkTo(methodOn(ProductoControllerV2.class).updateProducto(producto.getId(), producto)).withRel("actualizar"),
                linkTo(methodOn(ProductoControllerV2.class).deleteProducto(producto.getId())).withRel("eliminar"),
                linkTo(methodOn(ProductoControllerV2.class).patchProducto(producto.getId(), producto)).withRel("patch"),
                linkTo(methodOn(ProductoControllerV2.class).getProductosByMarcaId(producto.getMarca() != null ? producto.getMarca().getId() : null)).withRel("porMarca"),
                linkTo(methodOn(ProductoControllerV2.class).getProductosByCategoriaId((producto.getCategoriaProductos() != null && !producto.getCategoriaProductos().isEmpty() && producto.getCategoriaProductos().get(0).getCategoria() != null) ? producto.getCategoriaProductos().get(0).getCategoria().getId() : null)).withRel("porCategoria"),
                linkTo(methodOn(ProductoControllerV2.class).getProductosByPrecioBetween(0, producto.getPrecio() != null ? producto.getPrecio() : 0)).withRel("porPrecio"),
                linkTo(methodOn(ProductoControllerV2.class).getProductosByCalificacionBetween(1, 5)).withRel("porCalificacion"),
                linkTo(methodOn(ProductoControllerV2.class).getProductosBySedeId(1)).withRel("porSede"),
                linkTo(methodOn(ProductoControllerV2.class).getProductosByMarcaIdAndCategoriaIdAndSedeId(
                    producto.getMarca() != null ? producto.getMarca().getId() : 1,
                    (producto.getCategoriaProductos() != null && !producto.getCategoriaProductos().isEmpty() && producto.getCategoriaProductos().get(0).getCategoria() != null) ? producto.getCategoriaProductos().get(0).getCategoria().getId() : 1,
                    1)).withRel("porMarcaCategoriaSede"),
                linkTo(methodOn(ProductoControllerV2.class).getProductosBySedeIdAndCategoriaId(
                    1,
                    (producto.getCategoriaProductos() != null && !producto.getCategoriaProductos().isEmpty() && producto.getCategoriaProductos().get(0).getCategoria() != null) ? producto.getCategoriaProductos().get(0).getCategoria().getId() : 1)).withRel("porSedeCategoria"),
                linkTo(methodOn(ProductoControllerV2.class).getProductosByMarcaIdAndSedeId(
                    producto.getMarca() != null ? producto.getMarca().getId() : 1,
                    1)).withRel("porMarcaSede")
        );
    }
} 