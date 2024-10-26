package com.arrienda.proyecto.servicios;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.modelos.*;
import com.arrienda.proyecto.repositorios.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

        initializeFields(dtoPropiedad);

        Propiedad propiedad = modelMapper.map(dtoPropiedad, Propiedad.class);
    
        repositorioArrendador.findById(dtoPropiedad.getArrendadorId())
                .orElseThrow(() -> new RuntimeException("Arrendador not found"));
    


        Propiedad savedPropiedad = repositorioPropiedad.save(propiedad);
        return modelMapper.map(savedPropiedad, DTOPropiedad.class);
    }

    private void initializeFields(DTOPropiedad dtoPropiedad) {
        if (dtoPropiedad.getNombre() == null) {
            dtoPropiedad.setNombre("");
        }
        if (dtoPropiedad.getUbicacion() == null) {
            dtoPropiedad.setUbicacion("");
        }
        if (dtoPropiedad.isParqueadero() == false) {
            dtoPropiedad.setParqueadero(false);
        }
        if (dtoPropiedad.isPiscina() == false) {
            dtoPropiedad.setPiscina(false);
        }
        if (dtoPropiedad.getCuartos() == 0) {
            dtoPropiedad.setCuartos(0);
        }
        if (dtoPropiedad.getCamas() == 0) {
            dtoPropiedad.setCamas(0);
        }
        if (dtoPropiedad.getArea() == 0.0f) {
            dtoPropiedad.setArea(0.0f);
        }
        if (dtoPropiedad.getCapacidad() == 0) {
            dtoPropiedad.setCapacidad(0);
        }
        if (dtoPropiedad.isDisponible() == false) {
            dtoPropiedad.setDisponible(false);
        }
        if (dtoPropiedad.getPrecioXnoche() == 0L) {
            dtoPropiedad.setPrecioXnoche(0L);
        }
        if (dtoPropiedad.getStatus() == 0) {
            dtoPropiedad.setStatus(0);
        }
        if (dtoPropiedad.getArrendadorId() == 0L) {
            dtoPropiedad.setArrendadorId(0L);
        }
        if (dtoPropiedad.getCalificionPromedio() == 0.0f) {
            dtoPropiedad.setCalificionPromedio(0.0f);
        }
        if (dtoPropiedad.getCalificaciones() == null) {
            dtoPropiedad.setCalificaciones(new ArrayList<>());
        }
        if (dtoPropiedad.getSolicitudes() == null) {
            dtoPropiedad.setSolicitudes(new ArrayList<>());
        }
        if (dtoPropiedad.getLavanderia() == null) {
            dtoPropiedad.setLavanderia(false);
        }
        if (dtoPropiedad.getWifi() == null) {
            dtoPropiedad.setWifi(false);
        }
        if (dtoPropiedad.getMascotas() == null) {
            dtoPropiedad.setMascotas(false);
        }
        if (dtoPropiedad.getGimnasios() == null) {
            dtoPropiedad.setGimnasios(false);
        }
        if (dtoPropiedad.getZonaJuegos() == null) {
            dtoPropiedad.setZonaJuegos(false);
        }
        if (dtoPropiedad.getAlimentacion() == null) {
            dtoPropiedad.setAlimentacion(false);
        }
        if (dtoPropiedad.getPropiedad() == null) {
            dtoPropiedad.setPropiedad("");
        }
        if (dtoPropiedad.getImagenes() == null) {
            dtoPropiedad.setImagenes(new ArrayList<>());
        }
    }

    // Actualizar una propiedad existente
    public DTOPropiedad actualizarPropiedad(Long id, DTOPropiedad dtoPropiedad) {
        Propiedad existingPropiedad = repositorioPropiedad.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Propiedad no encontrada"));
    
        modelMapper.map(dtoPropiedad, existingPropiedad);
        Propiedad updatedPropiedad = repositorioPropiedad.save(existingPropiedad);
        return modelMapper.map(updatedPropiedad, DTOPropiedad.class);
    }

    @Transactional
    public void eliminarPropiedad(Long id) {
        try {
            repositorioPropiedad.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Propiedad no encontrada"));
            
            List<Calificacion> calificacionesPropiedad = repositorioCalificacion.findByIdCalificadoAndIdTipo(id, 2);
            repositorioCalificacion.deleteAll(calificacionesPropiedad);

            List<Solicitud> solicitudesPropiedad = repositorioSolicitud.findByPropiedadId(id);
            repositorioSolicitud.deleteAll(solicitudesPropiedad);

            repositorioPropiedad.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la propiedad", e);
        }
    }

    void actualizarPromedioCalificacion(Long id, float calificacionPromedio) {
        Propiedad propiedad = repositorioPropiedad.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Propiedad no encontrada"));
                propiedad.setCalificionPromedio(calificacionPromedio);
        repositorioPropiedad.save(propiedad);
    }

}
