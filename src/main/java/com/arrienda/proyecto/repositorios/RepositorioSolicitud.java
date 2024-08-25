package com.arrienda.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arrienda.proyecto.modelos.Solicitud;

public interface RepositorioSolicitud extends JpaRepository<Solicitud, Long>{

    Solicitud findBySolicitudId(Long id);

    List<Solicitud> findByStatus(int estado);

    List<Solicitud> findByArrendatarioId(Long arrendatarioId);
    
}
