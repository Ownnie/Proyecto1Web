package com.arrienda.proyecto.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arrienda.proyecto.dtos.DTOPropiedad;
import com.arrienda.proyecto.servicios.ServicioPropiedad;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/propiedad")
public class ControllerPropiedad {
    @Autowired
    private ServicioPropiedad servicioPropiedad;

    @GetMapping("/propiedades")
    public List<DTOPropiedad> traerPropiedades(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.traerPropiedades();
    }

    @GetMapping("/propiedad/{id}")
    public DTOPropiedad obtenerPropiedad(Authentication authentication, @PathVariable Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.obtenerPropiedad(id);
    }

    @GetMapping("/camas/{camas}")
    public List<DTOPropiedad> obtenerPropiedadesPorCamas(Authentication authentication, @PathVariable int camas) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.obtenerPropiedadesPorCamas(camas);
    }

    @GetMapping("/disponibles")
    public List<DTOPropiedad> obtenerPropiedadesDisponibles(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.obtenerPropiedadesDisponibles();
    }

    @GetMapping("/cuartos/{cuartos}")
    public List<DTOPropiedad> obtenerPropiedadesPorCuartos(Authentication authentication, @PathVariable int cuartos) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.obtenerPropiedadesPorCuartos(cuartos);
    }

    @GetMapping("/area/{area}")
    public List<DTOPropiedad> obtenerPropiedadesPorArea(Authentication authentication, @PathVariable float area) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.obtenerPropiedadesPorArea(area);
    }

    @GetMapping("/capacidad/{capacidad}")
    public List<DTOPropiedad> obtenerPropiedadesPorCapacidad(Authentication authentication,
            @PathVariable int capacidad) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.obtenerPropiedadesPorCapacidad(capacidad);
    }

    @GetMapping("/arrendador/{id}/propiedades")
    public List<DTOPropiedad> getPropiedades(Authentication authentication, @PathVariable Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.getPropiedades(id);
    }

    @PostMapping("/crearPropiedad")
    public DTOPropiedad crearPropiedad(Authentication authentication, @RequestBody DTOPropiedad propiedad) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.crearPropiedad(propiedad);
    }

    @PutMapping("/actualizarPropiedad/{id}")
    public DTOPropiedad actualizarPropiedad(Authentication authentication, @PathVariable Long id,
            @RequestBody DTOPropiedad propiedad) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioPropiedad.actualizarPropiedad(id, propiedad);
    }

    @DeleteMapping("/eliminarPropiedad/{id}")
    public ResponseEntity<String> eliminarPropiedad(Authentication authentication, @PathVariable Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        try {
            servicioPropiedad.eliminarPropiedad(id);
            return ResponseEntity.ok("Propiedad eliminada con éxito.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Propiedad no encontrada.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la propiedad.");
        }
    }

}