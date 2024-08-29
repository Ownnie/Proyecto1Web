package com.arrienda.proyecto.servicios;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.arrienda.proyecto.repositorios.RepositorioArrendatario;
import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.modelos.*;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicioArrendatario {
    
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private final RepositorioArrendatario repositorioArrendatario;

    public ServicioArrendatario(RepositorioArrendatario repositorioArrendatario) {
        this.repositorioArrendatario = repositorioArrendatario;
    }

    public List<DTOArrendatario> getAllArrendatarios() {
        return repositorioArrendatario.findAll().stream()
                .map(arrendatario -> modelMapper.map(arrendatario, DTOArrendatario.class))
                .collect(Collectors.toList());
    }

    public DTOArrendatario getArrendatario(Long id) {
        Arrendatario arrendatario = repositorioArrendatario.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendatario no encontrado"));
        return modelMapper.map(arrendatario, DTOArrendatario.class);
    }

    public DTOArrendatario createArrendatario(DTOArrendatario dtoArrendatario) {
        if (repositorioArrendatario.existsByUsuario(dtoArrendatario.getUsuario())) {
            throw new IllegalArgumentException("El arrendatario con este email ya existe.");
        }

        Arrendatario arrendatario = modelMapper.map(dtoArrendatario, Arrendatario.class);
        Arrendatario savedArrendatario = repositorioArrendatario.save(arrendatario);
        return modelMapper.map(savedArrendatario, DTOArrendatario.class);
    }

    public DTOArrendatario updateArrendatario(Long id, DTOArrendatario dtoArrendatario) {
        Arrendatario existingArrendatario = repositorioArrendatario.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendatario no encontrado"));

        modelMapper.map(dtoArrendatario, existingArrendatario);
        Arrendatario updatedArrendatario = repositorioArrendatario.save(existingArrendatario);
        return modelMapper.map(updatedArrendatario, DTOArrendatario.class);

    }

    public void eliminarArrendatario(Long id) {
        Arrendatario existingArrendatario = repositorioArrendatario.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendatario no encontrado"));
                repositorioArrendatario.delete(existingArrendatario);
    }

    public List<DTOCalificacion> getCalificaciones(Long id) {
        Arrendatario arrendatario = repositorioArrendatario.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendatario no encontrado"));
        return arrendatario.getCalificaciones().stream()
                .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                .collect(Collectors.toList());
    }    

    public List<DTOSolicitud> getSolicitudes(Long id) {
        Arrendatario arrendatario = repositorioArrendatario.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendatario no encontrado"));
        return arrendatario.getSolicitudes().stream()
                .map(solicitud -> modelMapper.map(solicitud, DTOSolicitud.class))
                .collect(Collectors.toList());
    }

}