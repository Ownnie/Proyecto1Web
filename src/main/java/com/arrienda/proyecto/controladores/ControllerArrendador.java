package com.arrienda.proyecto.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/arrendador")
public class ControllerArrendador {

    @Autowired
    private ServicioArrendador servicioArrendador;

    @GetMapping("/arrendadores")
    public List<DTOArrendador> getAllArrendadores() {
        return servicioArrendador.traerArrendadores();
    }

    @GetMapping("/arrendador/{id}")
    public DTOArrendador getArrendador(@PathVariable Long id) {
        return servicioArrendador.traerArrendador(id);
    }

    @PostMapping("/crearArrendador")
    public DTOArrendador crearArrendador(@RequestBody DTOArrendadorContrasena arrendador) {
        return servicioArrendador.crearArrendador(arrendador);
    }

    @PutMapping("/actualizarArrendador/{id}")
    public DTOArrendador actualizarArrendador(@PathVariable Long id, @RequestBody DTOArrendadorContrasena arrendador) {
        return servicioArrendador.actualizarArrendador(id, arrendador);
    }

    @DeleteMapping("/eliminarArrendador/{id}")
    public ResponseEntity<String> eliminarArrendador(@PathVariable Long id) {
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