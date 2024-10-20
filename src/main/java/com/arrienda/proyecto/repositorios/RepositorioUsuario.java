package com.arrienda.proyecto.repositorios;

import java.util.Optional;
import com.arrienda.proyecto.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    Usuario findByUsuario(String usuario);

    Optional<Usuario> findById(Long id);

    boolean existsByUsuario(String usuario);
}
