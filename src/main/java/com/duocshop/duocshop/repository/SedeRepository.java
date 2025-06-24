package com.duocshop.duocshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.duocshop.duocshop.model.Sede;

@Repository
public interface SedeRepository extends JpaRepository <Sede, Integer> {
    
}
