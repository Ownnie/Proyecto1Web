package com.arrienda.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arrienda.proyecto.modelos.Solicitud;

public interface RepositorioSolicitud extends JpaRepository<Solicitud, Long>{
    
}
