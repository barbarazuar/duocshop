package com.duocshop.duocshop.controller.v2;

import com.duocshop.duocshop.dto.UsuarioDTO;
import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.service.UsuarioService;
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
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> getAllUsuarios() {
        List<EntityModel<UsuarioDTO>> usuarios = usuarioService.obtenerUsuarios().stream()
                .map(UsuarioDTO::new)
                .map(usuarioDTO -> EntityModel.of(usuarioDTO,
                        linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuarioDTO.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"),
                        linkTo(methodOn(UsuarioControllerV2.class).createUsuario(new Usuario())).withRel("crear"),
                        linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuarioDTO.getId(), new Usuario())).withRel("actualizar"),
                        linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuarioDTO.getId())).withRel("eliminar"),
                        linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuarioDTO.getId(), new Usuario())).withRel("patch")))
                .collect(Collectors.toList());
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> getUsuarioById(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
            return ResponseEntity.ok(EntityModel.of(usuarioDTO,
                    linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuarioDTO.getId())).withSelfRel(),
                    linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"),
                    linkTo(methodOn(UsuarioControllerV2.class).createUsuario(new Usuario())).withRel("crear"),
                    linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuarioDTO.getId(), new Usuario())).withRel("actualizar"),
                    linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuarioDTO.getId())).withRel("eliminar"),
                    linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuarioDTO.getId(), new Usuario())).withRel("patch")));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> createUsuario(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioService.guardarUsuario(usuario);
        UsuarioDTO usuarioDTO = new UsuarioDTO(newUsuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuarioDTO.getId())).toUri())
                .body(EntityModel.of(usuarioDTO,
                        linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuarioDTO.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"),
                        linkTo(methodOn(UsuarioControllerV2.class).createUsuario(new Usuario())).withRel("crear"),
                        linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuarioDTO.getId(), new Usuario())).withRel("actualizar"),
                        linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuarioDTO.getId())).withRel("eliminar"),
                        linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuarioDTO.getId(), new Usuario())).withRel("patch")));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Usuario updatedUsuario = usuarioService.actualizarUsuario(id, usuario);
        UsuarioDTO usuarioDTO = new UsuarioDTO(updatedUsuario);
        return ResponseEntity.ok(EntityModel.of(usuarioDTO,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuarioDTO.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).createUsuario(new Usuario())).withRel("crear"),
                linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuarioDTO.getId(), new Usuario())).withRel("actualizar"),
                linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuarioDTO.getId())).withRel("eliminar"),
                linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuarioDTO.getId(), new Usuario())).withRel("patch")));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> patchUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Usuario updatedUsuario = usuarioService.actualizarUsuario(id, usuario);
        UsuarioDTO usuarioDTO = new UsuarioDTO(updatedUsuario);
        return ResponseEntity.ok(EntityModel.of(usuarioDTO,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuarioDTO.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).createUsuario(new Usuario())).withRel("crear"),
                linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuarioDTO.getId(), new Usuario())).withRel("actualizar"),
                linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuarioDTO.getId())).withRel("eliminar"),
                linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuarioDTO.getId(), new Usuario())).withRel("patch")));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 