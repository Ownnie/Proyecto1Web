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

import com.arrienda.proyecto.dtos.DTOArrendador;
import com.arrienda.proyecto.dtos.DTOArrendadorContrasena;
import com.arrienda.proyecto.servicios.ServicioArrendador;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/arrendador")
public class ControllerArrendador {

    @Autowired
    private ServicioArrendador servicioArrendador;

    @GetMapping("/arrendadores")
    public List<DTOArrendador> getAllArrendadores(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioArrendador.traerArrendadores();
    }

    @GetMapping("/arrendador/{id}")
    public DTOArrendador getArrendador(Authentication authentication, @PathVariable Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioArrendador.traerArrendador(id);
    }

    @GetMapping("/arrendador/arrendador-actual")
    public DTOArrendador getArrendadorActual(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        DTOArrendador arrendador = servicioArrendador.autorizacion(authentication);
        if (arrendador == null) {
            return null;
        }

        return arrendador;
    }

    @PostMapping("/crearArrendador")
    public DTOArrendador crearArrendador(Authentication authentication,
            @RequestBody DTOArrendadorContrasena arrendador) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioArrendador.crearArrendador(arrendador);
    }

    @PutMapping("/actualizarArrendador/{id}")
    public DTOArrendador actualizarArrendador(Authentication authentication, @PathVariable Long id,
            @RequestBody DTOArrendadorContrasena arrendador) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioArrendador.actualizarArrendador(id, arrendador);
    }

    @DeleteMapping("/eliminarArrendador/{id}")
    public ResponseEntity<String> eliminarArrendador(Authentication authentication, @PathVariable Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        try {
            servicioArrendador.eliminarArrendador(id);
            return ResponseEntity.ok("Arrendador eliminado con éxito.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arrendador no encontrado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el arrendador.");
        }
    }
}