package com.duocshop.duocshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "calificacion")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Calificacion {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int puntaje; 

    @Column(nullable = false)
    private String comentario;

    @OneToMany(mappedBy = "calificacion")
    @JsonIgnore
    private List<UsuarioCalificacion> usuarioCalificaciones;

}
  

