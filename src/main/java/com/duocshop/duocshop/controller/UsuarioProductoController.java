package com.duocshop.duocshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duocshop.duocshop.model.UsuarioProducto;
import com.duocshop.duocshop.service.UsuarioProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "UsuarioProducto", description = "Operaciones relacionadas con los productos de usuarios")
@RestController
@RequestMapping("/api/v1/usuarios-productos")
public class UsuarioProductoController {

    @Autowired
    private UsuarioProductoService usuarioProductoService;

    @Operation(summary = "Listar productos de usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuario-productos obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay usuario-productos disponibles")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioProducto>> listar() {
        List<UsuarioProducto> usuarioProductos = usuarioProductoService.obtenerUsuarioProductos();
        if (usuarioProductos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarioProductos);
    }

    @Operation(summary = "Guardar un nuevo producto de usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario-producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<UsuarioProducto> guardar(@RequestBody UsuarioProducto usuarioProducto) {
        UsuarioProducto nuevoUsuarioProducto = usuarioProductoService.guardarUsuarioProducto(usuarioProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuarioProducto);
    }

    @Operation(summary = "Actualizar un producto de usuario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario-producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario-producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioProducto> actualizar(@PathVariable Integer id, @RequestBody UsuarioProducto usuarioProducto) {
        try {
            usuarioProducto.setId(id);
            UsuarioProducto actualizado = usuarioProductoService.actualizarUsuarioProducto(id, usuarioProducto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar parcialmente un producto de usuario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario-producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario-producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioProducto> patchUsuarioProducto(@PathVariable Integer id, @RequestBody UsuarioProducto usuarioProducto) {
        try {
            usuarioProducto.setId(id);
            UsuarioProducto updatedUsuarioProducto = usuarioProductoService.actualizarUsuarioProducto(id, usuarioProducto);
            return ResponseEntity.ok(updatedUsuarioProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            usuarioProductoService.eliminarUsuarioProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/resumen")
    public ResponseEntity<List<Map<String, Object>>> resumen() {
        List<Map<String, Object>> resumen = usuarioProductoService.obtenerUsuarioProducto();
        if (resumen.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioProducto> getUsuarioProductoById(@PathVariable Integer id) {
        UsuarioProducto usuarioProducto = usuarioProductoService.obtenerUsuarioProductoPorId(id);
        if (usuarioProducto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioProducto);
    }
}
