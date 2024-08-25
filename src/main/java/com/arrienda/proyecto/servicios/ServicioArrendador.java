package com.arrienda.proyecto.servicios;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.arrienda.proyecto.dtos.DTOArrendador;
import com.arrienda.proyecto.dtos.DTOCalificacion;
import com.arrienda.proyecto.dtos.DTOPropiedad;
import com.arrienda.proyecto.dtos.DTOSolicitud;
import com.arrienda.proyecto.modelos.*;
import com.arrienda.proyecto.repositorios.*;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicioArrendador {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private final RepositorioArrendador repositorioArrendador;

    
    public ServicioArrendador(RepositorioArrendador repositorioArrendador) {
        this.repositorioArrendador = repositorioArrendador;
    }

    public List<DTOArrendador> traerArrendadores() {
        return repositorioArrendador.findAll().stream()
                .map(arrendador -> modelMapper.map(arrendador, DTOArrendador.class))
                .collect(Collectors.toList());
    }

    public DTOArrendador traerArrendador(Long id) {
        Arrendador arrendador = repositorioArrendador.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));
        return modelMapper.map(arrendador, DTOArrendador.class);
    }

    public List<DTOCalificacion> getCalificaciones(Long id) {
        Arrendador arrendador = repositorioArrendador.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));
        return arrendador.getCalificaciones().stream()
                .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                .collect(Collectors.toList());
    }    

    public List<DTOPropiedad> getPropiedades(Long id) {
        Arrendador arrendador = repositorioArrendador.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));
        return arrendador.getPropiedades().stream()
                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                .collect(Collectors.toList());
    }

    public DTOArrendador crearArrendador(DTOArrendador dtoArrendador) {
        Arrendador arrendador = modelMapper.map(dtoArrendador, Arrendador.class);
        Arrendador savedArrendador = repositorioArrendador.save(arrendador);
        return modelMapper.map(savedArrendador, DTOArrendador.class);
    }


    public DTOArrendador actualizarArrendador(Long id, DTOArrendador dtoArrendador) {
        Arrendador existingArrendador = repositorioArrendador.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));

        modelMapper.map(dtoArrendador, existingArrendador);
        Arrendador updatedArrendador = repositorioArrendador.save(existingArrendador);
        return modelMapper.map(updatedArrendador, DTOArrendador.class);
    }

    public void eliminarArrendador(Long id) {
        Arrendador existingArrendador = repositorioArrendador.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));
        repositorioArrendador.delete(existingArrendador);
    }
} 