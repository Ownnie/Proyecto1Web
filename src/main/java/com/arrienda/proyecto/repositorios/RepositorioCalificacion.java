package com.arrienda.proyecto.repositorios;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import com.arrienda.proyecto.modelos.Calificacion;


public interface RepositorioCalificacion extends JpaRepository <Calificacion, Long> {
    Optional<Calificacion> findById(Long id);

    List<Calificacion> findByIdCalificadoAndIdTipo(Long id, int i);


}
