package com.arrienda.proyecto.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import com.arrienda.proyecto.dtos.DTOPropiedad;
import com.arrienda.proyecto.modelos.Propiedad;
import com.arrienda.proyecto.repositorios.RepositorioPropiedad;

@Service
public class ServicioPropiedad {

    @Autowired
    private RepositorioPropiedad repositorioPropiedad;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todas las propiedades
    public List<DTOPropiedad> traerPropiedades() {
        return repositorioPropiedad.findAll().stream()
                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                .collect(Collectors.toList());
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

    // Crear una nueva propiedad
    public DTOPropiedad crearPropiedad(DTOPropiedad dtoPropiedad) {
        Propiedad propiedad = modelMapper.map(dtoPropiedad, Propiedad.class);
        Propiedad savedPropiedad = repositorioPropiedad.save(propiedad);
        return modelMapper.map(savedPropiedad, DTOPropiedad.class);
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
