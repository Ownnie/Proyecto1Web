package com.arrienda.proyecto.controladores;

import java.util.*;
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

import com.arrienda.proyecto.dtos.DTOUsuario;
import com.arrienda.proyecto.servicios.ServicioUsuario;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/usuario")
public class ControllerUsuario {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping("/usuarios")
    public List<DTOUsuario> getAllUsuarioes() {
        return servicioUsuario.traerUsuarios();
    }

    @GetMapping("/usuario/{id}")
    public DTOUsuario getUsuario(@PathVariable Long id) {
        return servicioUsuario.traerUsuario(id);
    }

    @PostMapping("/crearUsuario")
    public DTOUsuario crearUsuario(@RequestBody DTOUsuario usuario) {
        return servicioUsuario.crearUsuario(usuario);
    }

    @PutMapping("/actualizarUsuario/{id}")
    public DTOUsuario actualizarUsuario(@PathVariable Long id, @RequestBody DTOUsuario usuario) {
        return servicioUsuario.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        try {
            servicioUsuario.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado con éxito.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el Usuario.");
        }
    }
}
