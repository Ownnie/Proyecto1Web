package com.arrienda.proyecto.controladores;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.servicios.*;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/arrendatario")
public class ControllerArrendatario {

    @Autowired
    private ServicioArrendatario servicioArrendatario;

    @GetMapping("/arrendatarios")
    public List<DTOArrendatario> getAllArrendatarios() {
        return servicioArrendatario.getAllArrendatarios();
    }

    @GetMapping("/arrendatario/{id}")
    public DTOArrendatario getArrendatario(@PathVariable Long id) {
        return servicioArrendatario.getArrendatario(id);
    }

    @PostMapping("/crearArrendatario")
    public DTOArrendatario crearArrendatario(@RequestBody DTOArrendatario arrendatario) {
        return servicioArrendatario.createArrendatario(arrendatario);
    }

    @PutMapping("/actualizarArrendatario/{id}")
    public DTOArrendatario actualizarArrendatario(@PathVariable Long id, @RequestBody DTOArrendatario arrendatario) {
        return servicioArrendatario.updateArrendatario(id, arrendatario);
    }

    @DeleteMapping("/eliminarArrendatario/{id}")
    public ResponseEntity<String> eliminarArrendatario(@PathVariable Long id) {
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