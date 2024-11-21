package com.arrienda.proyecto.controladores;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.servicios.*;
import org.springframework.security.core.Authentication;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/arrendatario")
public class ControllerArrendatario {

    @Autowired
    private ServicioArrendatario servicioArrendatario;

    @GetMapping("/arrendatarios")
    public List<DTOArrendatario> getAllArrendatarios(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return servicioArrendatario.getAllArrendatarios();
    }

    @GetMapping("/arrendatario/{id}")
    public DTOArrendatario getArrendatario(Authentication authentication, @PathVariable Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioArrendatario.getArrendatario(id);
    }

    @PostMapping("/crearArrendatario")
    public DTOArrendatario crearArrendatario(Authentication authentication,
            @RequestBody DTOArrendatarioContrasena arrendatario) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioArrendatario.createArrendatario(arrendatario);
    }

    @PutMapping("/actualizarArrendatario/{id}")
    public DTOArrendatario actualizarArrendatario(Authentication authentication, @PathVariable Long id,
            @RequestBody DTOArrendatarioContrasena arrendatario) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return servicioArrendatario.updateArrendatario(id, arrendatario);
    }

    @DeleteMapping("/eliminarArrendatario/{id}")
    public ResponseEntity<String> eliminarArrendatario(Authentication authentication, @PathVariable Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        try {
            servicioArrendatario.eliminarArrendatario(id);
            return ResponseEntity.ok("Arrendatario eliminado con éxito.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arrendatario no encontrado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el Arrendatario.");
        }
    }

}