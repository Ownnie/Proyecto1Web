package com.arrienda.proyecto.servicios;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;

import com.arrienda.proyecto.dtos.DTOCalificacion;
import com.arrienda.proyecto.dtos.DTOPropiedad;
import com.arrienda.proyecto.dtos.DTOSolicitud;
import com.arrienda.proyecto.modelos.Arrendador;
import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.modelos.Propiedad;
import com.arrienda.proyecto.modelos.Solicitud;
import com.arrienda.proyecto.repositorios.*;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicioPropiedad {

    @Autowired
    private RepositorioPropiedad repositorioPropiedad;

    @Autowired
    private RepositorioArrendador repositorioArrendador;

    @Autowired
    private RepositorioCalificacion repositorioCalificacion;

    @Autowired
    private RepositorioSolicitud repositorioSolicitud;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todas las propiedades
    public List<DTOPropiedad> traerPropiedades() {
    try {
        List<DTOPropiedad> propiedades = repositorioPropiedad.findAll().stream()
                .map(propiedad -> {
                    DTOPropiedad dtoPropiedad = modelMapper.map(propiedad, DTOPropiedad.class);

                    List<Solicitud> solicitudes = repositorioSolicitud.findByPropiedadId(propiedad.getId());
                    List<DTOSolicitud> dtoSolicitudes = solicitudes.stream()
                            .map(solicitud -> modelMapper.map(solicitud, DTOSolicitud.class))
                            .collect(Collectors.toList());
                    dtoPropiedad.setSolicitudes(dtoSolicitudes);

                    List<Calificacion> calificaciones = repositorioCalificacion.findByIdCalificadoAndIdTipo(propiedad.getId(), 2);
                    List<DTOCalificacion> dtoCalificaciones = calificaciones.stream()
                            .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                            .collect(Collectors.toList());
                    dtoPropiedad.setCalificaciones(dtoCalificaciones);

                    return dtoPropiedad;
                })
                .collect(Collectors.toList());
        return propiedades;
    } catch (Exception e) {
        throw e;
    }
}

    // Obtener una propiedad por su ID
    public DTOPropiedad obtenerPropiedad(Long id) {
        Propiedad propiedad = repositorioPropiedad.findById(id).orElse(null);
        return modelMapper.map(propiedad, DTOPropiedad.class);
    }

    // Obtener propiedades por camas
    public List<DTOPropiedad> obtenerPropiedadesPorCamas(int camas) {
        return repositorioPropiedad.findAll().stream()
                .filter(propiedad -> propiedad.getCamas() == camas)
                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                .collect(Collectors.toList());
    }

    // Obtener propiedades disponibles
    public List<DTOPropiedad> obtenerPropiedadesDisponibles() {
        return repositorioPropiedad.findAll().stream()
                .filter(Propiedad::isDisponible)
                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                .collect(Collectors.toList());
    }

    // Obtener propiedades por cuartos
    public List<DTOPropiedad> obtenerPropiedadesPorCuartos(int cuartos) {
        return repositorioPropiedad.findAll().stream()
                .filter(propiedad -> propiedad.getCuartos() == cuartos)
                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                .collect(Collectors.toList());
    }

    // Obtener propiedades por área
    public List<DTOPropiedad> obtenerPropiedadesPorArea(float area) {
        return repositorioPropiedad.findAll().stream()
                .filter(propiedad -> propiedad.getArea() == area)
                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                .collect(Collectors.toList());
    }

    // Obtener propiedades por capacidad
    public List<DTOPropiedad> obtenerPropiedadesPorCapacidad(int capacidad) {
        return repositorioPropiedad.findAll().stream()
                .filter(propiedad -> propiedad.getCapacidad() == capacidad)
                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                .collect(Collectors.toList());
    }

    // Obtener propiedades del propietario
    public List<DTOPropiedad> getPropiedades(Long id) {
        return repositorioPropiedad.findAll().stream()
                .filter(propiedad -> propiedad.getArrendadorId() == id)
                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                .collect(Collectors.toList());
    }

    // Crear una nueva propiedad
    public DTOPropiedad crearPropiedad(DTOPropiedad dtoPropiedad) {
        Propiedad propiedad = new Propiedad();
        propiedad.setNombre(dtoPropiedad.getNombre());
        propiedad.setUbicacion(dtoPropiedad.getUbicacion());
        propiedad.setParqueadero(dtoPropiedad.isParqueadero());
        propiedad.setPiscina(dtoPropiedad.isPiscina());
        propiedad.setCuartos(dtoPropiedad.getCuartos());
        propiedad.setCamas(dtoPropiedad.getCamas());
        propiedad.setArea(dtoPropiedad.getArea());
        propiedad.setCapacidad(dtoPropiedad.getCapacidad());
        propiedad.setDisponible(dtoPropiedad.isDisponible());
        propiedad.setPrecioXnoche(dtoPropiedad.getPrecioXnoche());
        propiedad.setStatus(dtoPropiedad.getStatus());

        // Verify that the arrendadorId exists
        repositorioArrendador.findById(dtoPropiedad.getArrendadorId())
                .orElseThrow(() -> new RuntimeException("Arrendador not found"));

        // Set the arrendadorId in the propiedad
        propiedad.setArrendadorId(dtoPropiedad.getArrendadorId());

        Propiedad savedPropiedad = repositorioPropiedad.save(propiedad);

        // Map the saved Propiedad back to DTOPropiedad
        DTOPropiedad result = new DTOPropiedad();
        result.setId(savedPropiedad.getId());
        result.setNombre(savedPropiedad.getNombre());
        result.setUbicacion(savedPropiedad.getUbicacion());
        result.setParqueadero(savedPropiedad.isParqueadero());
        result.setPiscina(savedPropiedad.isPiscina());
        result.setCuartos(savedPropiedad.getCuartos());
        result.setCamas(savedPropiedad.getCamas());
        result.setArea(savedPropiedad.getArea());
        result.setCapacidad(savedPropiedad.getCapacidad());
        result.setDisponible(savedPropiedad.isDisponible());
        result.setPrecioXnoche(savedPropiedad.getPrecioXnoche());
        result.setStatus(savedPropiedad.getStatus());
        result.setArrendadorId(savedPropiedad.getArrendadorId());

        return result;
    }

    // Actualizar una propiedad existente
    public DTOPropiedad actualizarPropiedad(Long id, DTOPropiedad dtoPropiedad) {
        return repositorioPropiedad.findById(id)
                .map(existingPropiedad -> {
                    existingPropiedad.setNombre(dtoPropiedad.getNombre());
                    existingPropiedad.setUbicacion(dtoPropiedad.getUbicacion());
                    existingPropiedad.setParqueadero(dtoPropiedad.isParqueadero());
                    existingPropiedad.setPiscina(dtoPropiedad.isPiscina());
                    existingPropiedad.setCuartos(dtoPropiedad.getCuartos());
                    existingPropiedad.setCamas(dtoPropiedad.getCamas());
                    existingPropiedad.setArea(dtoPropiedad.getArea());
                    existingPropiedad.setCapacidad(dtoPropiedad.getCapacidad());
                    existingPropiedad.setDisponible(dtoPropiedad.isDisponible());
                    existingPropiedad.setPrecioXnoche(dtoPropiedad.getPrecioXnoche());
                    existingPropiedad.setStatus(dtoPropiedad.getStatus());
                    Propiedad updatedPropiedad = repositorioPropiedad.save(existingPropiedad);
                    return modelMapper.map(updatedPropiedad, DTOPropiedad.class);
                })
                .orElseGet(() -> {
                    Propiedad newPropiedad = modelMapper.map(dtoPropiedad, Propiedad.class);
                    newPropiedad.setId(id);
                    Propiedad savedPropiedad = repositorioPropiedad.save(newPropiedad);
                    return modelMapper.map(savedPropiedad, DTOPropiedad.class);
                });
    }

    // Eliminar una propiedad
    public void eliminarPropiedad(Long id) {
        repositorioPropiedad.deleteById(id);
    }

}
