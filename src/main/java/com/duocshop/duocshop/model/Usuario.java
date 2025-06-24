package com.duocshop.duocshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name= "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String  apellido;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false, length = 6)
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "sede_id", nullable = false)
    @JsonIgnore
    private Sede sede;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<UsuarioProducto> usuarioProductos;
  
 }
