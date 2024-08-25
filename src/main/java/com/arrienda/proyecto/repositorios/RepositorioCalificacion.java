package com.arrienda.proyecto.repositorios;

import org.springframework.data.jpa.repository.*;

import com.arrienda.proyecto.modelos.Arrendador;
import com.arrienda.proyecto.modelos.Calificacion;


public interface RepositorioCalificacion extends JpaRepository <Calificacion, Long> {
    Calificacion findByCalificacionId(Long id);
}
