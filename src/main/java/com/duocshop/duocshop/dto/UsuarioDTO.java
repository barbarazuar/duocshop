package com.duocshop.duocshop.dto;

import com.duocshop.duocshop.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    private Integer sedeId;
    private String sedeNombre;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.correo = usuario.getCorreo();
        if (usuario.getSede() != null) {
            this.sedeId = usuario.getSede().getId();
            this.sedeNombre = usuario.getSede().getNombre();
        }
    }
} 