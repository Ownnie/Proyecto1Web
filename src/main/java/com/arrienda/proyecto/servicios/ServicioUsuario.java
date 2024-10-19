package com.arrienda.proyecto.servicios;

import com.arrienda.proyecto.dtos.DTOUsuario;
import com.arrienda.proyecto.modelos.Usuario; // Asegúrate de importar la clase Usuario
import com.arrienda.proyecto.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario {

    @Autowired
    private RepositorioUsuario usuarioRepository;

    public Usuario findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    private Usuario convertirDTOaUsuario(DTOUsuario dtoUsuario) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(dtoUsuario.getUsuario());
        // Asigna otros campos según sea necesario
        return usuario;
    }
}
