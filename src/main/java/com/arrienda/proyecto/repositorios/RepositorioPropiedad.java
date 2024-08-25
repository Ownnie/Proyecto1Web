package com.arrienda.proyecto.repositorios;

import org.springframework.data.jpa.repository.*;
import com.arrienda.proyecto.modelos.Propiedad;

public interface RepositorioPropiedad extends JpaRepository <Propiedad, Long> {

    Propiedad findByPropiedadId(Long id);
    
}