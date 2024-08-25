package com.arrienda.proyecto.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.arrienda.proyecto.modelos.Arrendador;
import com.arrienda.proyecto.servicios.ServicioArrendador;

@RestController
@RequestMapping("/api/arrendadores")
public class ControllerArrendador {

    @Autowired
    private ServicioArrendador servicioArrendador;

    @GetMapping
    public List<Arrendador> getAllArrendadores() {
        return servicioArrendador.traerArrendador();
    }
    /*
     * 
     * @GetMapping("/{id}")
     * public ResponseEntity<Arrendador> getArrendadorById(@PathVariable Long id) {
     * Optional<Arrendador> arrendador = arrendadorService.findById(id);
     * return arrendador.map(ResponseEntity::ok).orElseGet(() ->
     * ResponseEntity.notFound().build());
     * }
     * 
     * @PostMapping
     * public Arrendador createArrendador(@RequestBody Arrendador arrendador) {
     * return arrendadorService.save(arrendador);
     * }
     * 
     * @PutMapping("/{id}")
     * public ResponseEntity<Arrendador> updateArrendador(@PathVariable Long
     * id, @RequestBody Arrendador arrendadorDetails) {
     * Optional<Arrendador> arrendador = arrendadorService.findById(id);
     * if (arrendador.isPresent()) {
     * Arrendador updatedArrendador = arrendador.get();
     * updatedArrendador.setUsuario(arrendadorDetails.getUsuario());
     * updatedArrendador.setContrasena(arrendadorDetails.getContrasena());
     * updatedArrendador.setNombre(arrendadorDetails.getNombre());
     * updatedArrendador.setStatus(arrendadorDetails.getStatus());
     * updatedArrendador.setPropiedades(arrendadorDetails.getPropiedades());
     * updatedArrendador.setCalificaciones(arrendadorDetails.getCalificaciones());
     * return ResponseEntity.ok(arrendadorService.save(updatedArrendador));
     * } else {
     * return ResponseEntity.notFound().build();
     * }
     * }
     * 
     * @DeleteMapping("/{id}")
     * public ResponseEntity<Void> deleteArrendador(@PathVariable Long id) {
     * if (arrendadorService.findById(id).isPresent()) {
     * arrendadorService.deleteById(id);
     * return ResponseEntity.ok().build();
     * } else {
     * return ResponseEntity.notFound().build();
     * }
     * }
     */

}