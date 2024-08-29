package com.arrienda.proyecto.repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import com.arrienda.proyecto.modelos.Arrendador;


public interface RepositorioArrendador extends JpaRepository <Arrendador, Long> {
    Optional<Arrendador> findById(Long id);

    boolean existsByUsuario(String usuario);
}