package com.arrienda.proyecto.repositorios;

import org.springframework.data.jpa.repository.*;

import com.arrienda.proyecto.modelos.Arrendatario;


public interface RepositorioArrendatario extends JpaRepository<Arrendatario, Long> {
    
}
