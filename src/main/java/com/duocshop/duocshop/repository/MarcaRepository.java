package com.duocshop.duocshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duocshop.duocshop.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    
} 