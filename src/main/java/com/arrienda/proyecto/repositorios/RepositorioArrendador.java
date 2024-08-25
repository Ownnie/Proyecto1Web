package com.arrienda.proyecto.repositorios;

import org.springframework.data.jpa.repository.*;

import com.arrienda.proyecto.modelos.Arrendador;


public interface RepositorioArrendador extends JpaRepository <Arrendador, Long> {
    Arrendador findByArrendadorId(Long id);
}