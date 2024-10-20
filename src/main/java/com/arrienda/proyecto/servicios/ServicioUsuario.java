package com.arrienda.proyecto.servicios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.modelos.*;
import com.arrienda.proyecto.repositorios.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ServicioUsuario {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioPropiedad repositorioPropiedad;

    @Autowired
    private RepositorioCalificacion repositorioCalificacion;

    @Autowired
    private RepositorioSolicitud repositorioSolicitud;

    public ServicioUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public List<DTOUsuario> traerUsuarios() {
        try {
            List<DTOUsuario> Usuarios = repositorioUsuario.findAll().stream()
                    .map(Usuario -> {
                        DTOUsuario dtoUsuario = modelMapper.map(Usuario, DTOUsuario.class);

                        return dtoUsuario;
                    })
                    .collect(Collectors.toList());
            return Usuarios;
        } catch (Exception e) {
            throw e;
        }
    }

    public DTOUsuario traerUsuario(Long id) {
        try {
            Usuario usuario = repositorioUsuario.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            DTOUsuario dtoUsuario = modelMapper.map(usuario, DTOUsuario.class);

            return dtoUsuario;
        } catch (Exception e) {
            throw e;
        }
    }

    public DTOUsuario crearUsuario(DTOUsuario dtoUsuario) {
        if (repositorioUsuario.existsByUsuario(dtoUsuario.getUsuario())) {
            throw new IllegalArgumentException("El Usuario con este usuario ya existe.");
        }

        Usuario usuario = modelMapper.map(dtoUsuario, Usuario.class);
        Usuario savedUsuario = repositorioUsuario.save(usuario);
        return modelMapper.map(savedUsuario, DTOUsuario.class);
    }

    public DTOUsuario actualizarUsuario(Long id, DTOUsuario dtoUsuario) {
        Usuario existingUsuario = repositorioUsuario.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        modelMapper.map(dtoUsuario, existingUsuario);
        Usuario updatedUsuario = repositorioUsuario.save(existingUsuario);
        return modelMapper.map(updatedUsuario, DTOUsuario.class);
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        try {
            Usuario existingUsuario = repositorioUsuario.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

            repositorioUsuario.delete(existingUsuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el Usuario", e);
        }
    }
}
