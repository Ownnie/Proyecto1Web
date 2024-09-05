package com.arrienda.proyecto.servicios;

import com.arrienda.proyecto.dtos.DTOUsuario;
import com.arrienda.proyecto.modelos.Usuario;
import com.arrienda.proyecto.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ServicioUsuarioDetalle implements UserDetailsService {

    @Autowired
    private RepositorioUsuario usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuario(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username);
        }

        // Retornar un UserDetails con una autoridad genérica "ROLE_USER"
        return new org.springframework.security.core.userdetails.User(
                usuario.getUsuario(),
                usuario.getContrasena(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); // Autoridad genérica
    }

    private Usuario convertirDTOaUsuario(DTOUsuario dtoUsuario) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(dtoUsuario.getUsuario());
        usuario.setContrasena(dtoUsuario.getContrasena());
        // Asigna otros campos según sea necesario
        return usuario;
    }

    public Usuario registrarUsuario(DTOUsuario dtoUsuario) {
        Usuario usuario = convertirDTOaUsuario(dtoUsuario);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }
}
