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
public class ServicioArrendador {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private final RepositorioArrendador repositorioArrendador;

    @Autowired
    private RepositorioPropiedad repositorioPropiedad;

    @Autowired
    private RepositorioCalificacion repositorioCalificacion;

    @Autowired
    private RepositorioSolicitud repositorioSolicitud;

    
    public ServicioArrendador(RepositorioArrendador repositorioArrendador) {
        this.repositorioArrendador = repositorioArrendador;
    }

    public List<DTOArrendador> traerArrendadores() {
        try {
            List<DTOArrendador> arrendadores = repositorioArrendador.findAll().stream()
                    .map(arrendador -> {
                        DTOArrendador dtoArrendador = modelMapper.map(arrendador, DTOArrendador.class);
    
                        List<Propiedad> propiedades = repositorioPropiedad.findPropiedadesByArrendadorId(arrendador.getId());
                        List<DTOPropiedad> dtoPropiedades = propiedades.stream()
                                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                                .collect(Collectors.toList());
                        dtoArrendador.setPropiedades(dtoPropiedades);
    
                        List<Calificacion> calificaciones = repositorioCalificacion.findByIdCalificadoAndIdTipo(arrendador.getId(), 0);
                        List<DTOCalificacion> dtoCalificaciones = calificaciones.stream()
                                .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                                .collect(Collectors.toList());
                        dtoArrendador.setCalificaciones(dtoCalificaciones);
    
                        return dtoArrendador;
                    })
                    .collect(Collectors.toList());
            return arrendadores;
        } catch (Exception e) {
            throw e;
        }
    }

    public DTOArrendador traerArrendador(Long id) {
        try {
            Arrendador arrendador = repositorioArrendador.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));
            DTOArrendador dtoArrendador = modelMapper.map(arrendador, DTOArrendador.class);
    
            List<Propiedad> propiedades = repositorioPropiedad.findPropiedadesByArrendadorId(arrendador.getId());
            List<DTOPropiedad> dtoPropiedades = propiedades.stream()
                    .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                    .collect(Collectors.toList());
            dtoArrendador.setPropiedades(dtoPropiedades);
    
            List<Calificacion> calificaciones = repositorioCalificacion.findByIdCalificadoAndIdTipo(arrendador.getId(), 0);
            List<DTOCalificacion> dtoCalificaciones = calificaciones.stream()
                    .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                    .collect(Collectors.toList());
            dtoArrendador.setCalificaciones(dtoCalificaciones);
    
            return dtoArrendador;
        } catch (Exception e) {
            throw e;
        }
    }

    public DTOArrendador crearArrendador(DTOArrendador dtoArrendador) {
        if (repositorioArrendador.existsByUsuario(dtoArrendador.getUsuario())) {
            throw new IllegalArgumentException("El arrendador con este usuario ya existe.");
        }
    
        dtoArrendador.setCalificionPromedio(0.0f);

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

    @Transactional
    public void eliminarArrendador(Long id) {
        try {
            Arrendador existingArrendador = repositorioArrendador.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));
    
            List<Propiedad> propiedades = repositorioPropiedad.findPropiedadesByArrendadorId(existingArrendador.getId());
    
            for (Propiedad propiedad : propiedades) {
                List<Calificacion> calificacionesPropiedad = repositorioCalificacion.findByIdCalificadoAndIdTipo(propiedad.getId(), 2);
                repositorioCalificacion.deleteAll(calificacionesPropiedad);
    
                List<Solicitud> solicitudesPropiedad = repositorioSolicitud.findByPropiedadId(propiedad.getId());
                repositorioSolicitud.deleteAll(solicitudesPropiedad);
            }
    
            repositorioPropiedad.deleteAll(propiedades);
    
            List<Calificacion> calificacionesArrendador = repositorioCalificacion.findByIdCalificadoAndIdTipo(existingArrendador.getId(), 0);
            repositorioCalificacion.deleteAll(calificacionesArrendador);
    
            repositorioArrendador.delete(existingArrendador);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el arrendador", e);
        }
    }

    void actualizarPromedioCalificacion(Long id, float calificacionPromedio) {
        Arrendador arrendador = repositorioArrendador.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));
        arrendador.setCalificionPromedio(calificacionPromedio);
        repositorioArrendador.save(arrendador);
    }

} 