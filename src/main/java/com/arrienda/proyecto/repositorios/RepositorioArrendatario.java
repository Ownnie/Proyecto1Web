package com.arrienda.proyecto.repositorios;

import org.springframework.data.jpa.repository.*;
import java.util.Optional;
import com.arrienda.proyecto.modelos.Arrendatario;


public interface RepositorioArrendatario extends JpaRepository<Arrendatario, Long> {
    Optional <Arrendatario> findById(Long id);

    boolean existsByUsuario(String usuario);
}
