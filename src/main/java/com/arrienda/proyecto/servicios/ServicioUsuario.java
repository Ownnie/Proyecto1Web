package com.arrienda.proyecto.servicios;

import com.arrienda.proyecto.dtos.DTOUsuario;
import com.arrienda.proyecto.modelos.Usuario; // Asegúrate de importar la clase Usuario
import com.arrienda.proyecto.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario {

    @Autowired
    private RepositorioUsuario usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(DTOUsuario dtoUsuario) {
        Usuario usuario = convertirDTOaUsuario(dtoUsuario);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    public Usuario findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    private Usuario convertirDTOaUsuario(DTOUsuario dtoUsuario) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(dtoUsuario.getUsuario());
        usuario.setContrasena(dtoUsuario.getContrasena());
        // Asigna otros campos según sea necesario
        return usuario;
    }
}