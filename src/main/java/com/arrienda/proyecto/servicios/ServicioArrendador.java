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

    public DTOArrendador crearArrendador(DTOArrendador dtoArrendador) {
        if (repositorioArrendador.existsByUsuario(dtoArrendador.getUsuario())) {
            throw new IllegalArgumentException("El arrendador con este usuario ya existe.");
        }

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