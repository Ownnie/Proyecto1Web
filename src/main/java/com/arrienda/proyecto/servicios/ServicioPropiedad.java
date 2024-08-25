package com.arrienda.proyecto.servicios;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arrienda.proyecto.modelos.Propiedad;
import com.arrienda.proyecto.repositorios.RepositorioPropiedad;

@Service
public class ServicioPropiedad {

    private final RepositorioPropiedad repositorioPropiedad;

    @Autowired
    public ServicioPropiedad(RepositorioPropiedad repositorioPropiedad) {
        this.repositorioPropiedad = repositorioPropiedad;
    }

    public List<Propiedad> traerPropiedades() {
        return repositorioPropiedad.findAll();
    }

    public Propiedad obtenerPropiedad(Long id) {
        return repositorioPropiedad.findByPropiedadId(id);
    }

    public List<Propiedad> obtenerPropiedadesPorCamas(int camas) {
        return repositorioPropiedad.findAll().stream()
                .filter(propiedad -> propiedad.getCamas() == camas)
                .collect(Collectors.toList());
    }

    public List<Propiedad> obtenerPropiedadesDisponibles() {
        return repositorioPropiedad.findAll().stream()
                .filter(Propiedad::isDisponible)
                .collect(Collectors.toList());
    }

    public List<Propiedad> obtenerPropiedadesPorCuartos(int cuartos) {
        return repositorioPropiedad.findAll().stream()
                .filter(propiedad -> propiedad.getCuartos() == cuartos)
                .collect(Collectors.toList());
    }

    public List<Propiedad> obtenerPropiedadesPorArea(float area) {
        return repositorioPropiedad.findAll().stream()
                .filter(propiedad -> propiedad.getArea() == area)
                .collect(Collectors.toList());
    }

    public List<Propiedad> obtenerPropiedadesPorCapacidad(int capacidad) {
        return repositorioPropiedad.findAll().stream()
                .filter(propiedad -> propiedad.getCapacidad() == capacidad)
                .collect(Collectors.toList());
    }

    public Propiedad crearPropiedad(Propiedad propiedad) {
        return repositorioPropiedad.save(propiedad);
    }

    public Propiedad actualizarPropiedad(Long id, Propiedad propiedad) {
        Optional<Propiedad> propiedadExistente = repositorioPropiedad.findById(id);
        if (propiedadExistente.isPresent()) {
            Propiedad actualizada = propiedadExistente.get();
            actualizada.setNombre(propiedad.getNombre());
            actualizada.setUbicacion(propiedad.getUbicacion());
            actualizada.setParqueadero(propiedad.isParqueadero());
            actualizada.setPiscina(propiedad.isPiscina());
            actualizada.setCuartos(propiedad.getCuartos());
            actualizada.setCamas(propiedad.getCamas());
            actualizada.setArea(propiedad.getArea());
            actualizada.setCapacidad(propiedad.getCapacidad());
            actualizada.setDisponible(propiedad.isDisponible());
            actualizada.setPrecioXnoche(propiedad.getPrecioXnoche());
            actualizada.setStatus(propiedad.getStatus());
            return repositorioPropiedad.save(actualizada);
        } else {
            throw new RuntimeException("Propiedad no encontrada con id " + id);
        }
    }

    public void eliminarPropiedad(Long id) {
        repositorioPropiedad.deleteById(id);
    }

}