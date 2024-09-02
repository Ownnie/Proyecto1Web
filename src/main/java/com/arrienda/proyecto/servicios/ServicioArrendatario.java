package com.arrienda.proyecto.servicios;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.arrienda.proyecto.repositorios.*;
import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.modelos.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ServicioArrendatario {
    
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private final RepositorioArrendatario repositorioArrendatario;

    @Autowired
    private RepositorioSolicitud repositorioSolicitud;

    @Autowired
    private RepositorioCalificacion repositorioCalificacion;

    public ServicioArrendatario(RepositorioArrendatario repositorioArrendatario) {
        this.repositorioArrendatario = repositorioArrendatario;
    }

    public List<DTOArrendatario> getAllArrendatarios() {
        try {
            List<DTOArrendatario> arrendatarios = repositorioArrendatario.findAll().stream()
                    .map(arrendatario -> {
                        DTOArrendatario dtoArrendatario = modelMapper.map(arrendatario, DTOArrendatario.class);

                        List<Solicitud> solicitudes = repositorioSolicitud.findSolicitudesByArrendatarioId(arrendatario.getId());
                        List<DTOSolicitud> dtoSolicitudes = solicitudes.stream()
                                .map(solicitud -> modelMapper.map(solicitud, DTOSolicitud.class))
                                .collect(Collectors.toList());
                        dtoArrendatario.setSolicitudes(dtoSolicitudes);

                        List<Calificacion> calificaciones = repositorioCalificacion.findByIdCalificadoAndIdTipo(arrendatario.getId(), 1);
                        List<DTOCalificacion> dtoCalificaciones = calificaciones.stream()
                                .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                                .collect(Collectors.toList());
                        dtoArrendatario.setCalificaciones(dtoCalificaciones);

                        return dtoArrendatario;
                    })
                    .collect(Collectors.toList());
            return arrendatarios;
        } catch (Exception e) {
            throw e;
        }
    }

    public DTOArrendatario getArrendatario(Long id) {
        try {
            Arrendatario arrendatario = repositorioArrendatario.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Arrendatario no encontrado"));
            DTOArrendatario dtoArrendatario = modelMapper.map(arrendatario, DTOArrendatario.class);

            // Fetch and map solicitudes
            List<Solicitud> solicitudes = repositorioSolicitud.findSolicitudesByArrendatarioId(arrendatario.getId());
            List<DTOSolicitud> dtoSolicitudes = solicitudes.stream()
                    .map(solicitud -> modelMapper.map(solicitud, DTOSolicitud.class))
                    .collect(Collectors.toList());
            dtoArrendatario.setSolicitudes(dtoSolicitudes);

            // Fetch and map calificaciones
            List<Calificacion> calificaciones = repositorioCalificacion.findByIdCalificadoAndIdTipo(arrendatario.getId(), 1);
            List<DTOCalificacion> dtoCalificaciones = calificaciones.stream()
                    .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                    .collect(Collectors.toList());
            dtoArrendatario.setCalificaciones(dtoCalificaciones);

            return dtoArrendatario;
        } catch (Exception e) {
            throw e;
        }
    }

    public DTOArrendatario createArrendatario(DTOArrendatario dtoArrendatario) {
        if (repositorioArrendatario.existsByUsuario(dtoArrendatario.getUsuario())) {
            throw new IllegalArgumentException("El arrendatario con este usuario ya existe.");
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

    @Transactional
    public void eliminarArrendatario(Long id) {
        try {
            Arrendatario existingArrendatario = repositorioArrendatario.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Arrendatario no encontrado"));

            List<Calificacion> calificacionesArrendatario = repositorioCalificacion.findByIdCalificadoAndIdTipo(id, 1);
            repositorioCalificacion.deleteAll(calificacionesArrendatario);

            List<Solicitud> solicitudesArrendatario = repositorioSolicitud.findByArrendatarioId(id);
            repositorioSolicitud.deleteAll(solicitudesArrendatario);

            repositorioArrendatario.delete(existingArrendatario);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el arrendatario", e);
        }
    }

}