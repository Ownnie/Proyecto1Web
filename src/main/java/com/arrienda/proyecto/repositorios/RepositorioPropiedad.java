package com.arrienda.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import com.arrienda.proyecto.modelos.Propiedad;

public interface RepositorioPropiedad extends JpaRepository <Propiedad, Long> {

    Optional<Propiedad> findById(Long id);
    
}