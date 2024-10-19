package com.arrienda.proyecto.repositorios;

import com.arrienda.proyecto.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    Usuario findByUsuario(String usuario);
}
