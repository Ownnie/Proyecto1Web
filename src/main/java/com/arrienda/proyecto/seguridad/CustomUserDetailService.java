package com.arrienda.proyecto.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arrienda.proyecto.modelos.Arrendador;
import com.arrienda.proyecto.modelos.Arrendatario;
import com.arrienda.proyecto.repositorios.RepositorioArrendador;
import com.arrienda.proyecto.repositorios.RepositorioArrendatario;

import lombok.Getter;

import java.util.Collections;
import java.util.Optional;

@Getter
@Service

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private RepositorioArrendador repositorioArrendador;

    @Autowired
    private RepositorioArrendatario repositorioArrendatario;

    private Arrendador arrendadorDetail;

    private Arrendatario arrendatarioDetail;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Arrendador> arrendador = repositorioArrendador.findByUsuario(username);
        Optional<Arrendatario> arrendatario = repositorioArrendatario.findByUsuario(username);

        if (arrendador.isPresent()) {
            arrendadorDetail = arrendador.get();
            return new org.springframework.security.core.userdetails.User(arrendadorDetail.getUsuario(),
                    arrendadorDetail.getContrasena(), Collections.emptyList());
        } else if (arrendatario.isPresent()) {
            arrendatarioDetail = arrendatario.get();
            return new org.springframework.security.core.userdetails.User(arrendatarioDetail.getUsuario(),
                    arrendatarioDetail.getContrasena(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

}
