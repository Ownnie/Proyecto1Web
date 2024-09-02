package com.arrienda.proyecto.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.modelos.*;
import com.arrienda.proyecto.repositorios.*;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicioSolicitud {

    @Autowired
    private RepositorioSolicitud repositorioSolicitud;

    @Autowired
    private RepositorioArrendatario repositorioArrendatario;

    @Autowired
    private RepositorioPropiedad repositorioPropiedad;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todas las solicitudes
    public List<DTOSolicitud> getAllSolicitudes() {
        return repositorioSolicitud.findAll().stream()
                .map(solicitud -> modelMapper.map(solicitud, DTOSolicitud.class))
                .collect(Collectors.toList());
    }

    // Obtener una solicitud por su ID
    public DTOSolicitud getSolicitud(Long id) {
        Solicitud solicitud = repositorioSolicitud.findById(id).orElse(null);
        return solicitud != null ? modelMapper.map(solicitud, DTOSolicitud.class) : null;
    }

    // Obtener solicitudes por estado
    public List<DTOSolicitud> getSolicitudesByEstado(int estado) {
        return repositorioSolicitud.findByStatus(estado).stream()
                .map(solicitud -> modelMapper.map(solicitud, DTOSolicitud.class))
                .collect(Collectors.toList());
    }

    // Obtener solicitudes por ID de arrendatario
    public List<DTOSolicitud> getSolicitudesByArrendatarioId(Long arrendatarioId) {
        return repositorioSolicitud.findByArrendatarioId(arrendatarioId).stream()
                .map(solicitud -> modelMapper.map(solicitud, DTOSolicitud.class))
                .collect(Collectors.toList());
    }

    // Obtener solicitudes por ID de propiedad
    public List<DTOSolicitud> getSolicitudesByPropiedadId(Long propiedadId) {
        return repositorioSolicitud.findByPropiedadId(propiedadId).stream()
                .map(solicitud -> modelMapper.map(solicitud, DTOSolicitud.class))
                .collect(Collectors.toList());
    }

    // Crear una nueva solicitud
    public DTOSolicitud crearSolicitud(DTOSolicitud dtoSolicitud) {
        Arrendatario arrendatario = repositorioArrendatario.findById(dtoSolicitud.getArrendatarioId())
                .orElseThrow(() -> new EntityNotFoundException("Arrendatario no encontrado"));

        Propiedad propiedad = repositorioPropiedad.findById(dtoSolicitud.getPropiedadId())
                .orElseThrow(() -> new EntityNotFoundException("Propiedad no encontrada"));

        Solicitud solicitud = modelMapper.map(dtoSolicitud, Solicitud.class);
        Solicitud savedSolicitud = repositorioSolicitud.save(solicitud);
        return modelMapper.map(savedSolicitud, DTOSolicitud.class);
    }

    // Actualizar una solicitud existente
    public DTOSolicitud actualizarSolicitud(Long id, DTOSolicitud dtoSolicitud) {
        return repositorioSolicitud.findById(id)
                .map(existingSolicitud -> {
                    modelMapper.map(dtoSolicitud, existingSolicitud);
                    Solicitud updatedSolicitud = repositorioSolicitud.save(existingSolicitud);
                    return modelMapper.map(updatedSolicitud, DTOSolicitud.class);
                })
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    // Eliminar una solicitud
    public void eliminarSolicitud(Long id) {
        Solicitud solicitud = repositorioSolicitud.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        repositorioSolicitud.deleteById(id);
    }
}
