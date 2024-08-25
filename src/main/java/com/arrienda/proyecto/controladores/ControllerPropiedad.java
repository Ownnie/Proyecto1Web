package com.arrienda.proyecto.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/propiedads")
public class ControllerPropiedad {
    @Autowired
    private ServicioPropiedad servicioPropiedad;

    @GetMapping("/propiedades")
    public List<DTOPropiedad> traerPropiedades() {
        return servicioPropiedad.traerPropiedades();
    }

    @GetMapping("/propiedad/{id}")
    public DTOPropiedad obtenerPropiedad(@PathVariable Long id) {
        return servicioPropiedad.obtenerPropiedad(id);
    }

    @GetMapping("/camas/{camas}")
    public List<DTOPropiedad> obtenerPropiedadesPorCamas(@PathVariable int camas) {
        return servicioPropiedad.obtenerPropiedadesPorCamas(camas);
    }

    @GetMapping("/disponibles")
    public List<DTOPropiedad> obtenerPropiedadesDisponibles() {
        return servicioPropiedad.obtenerPropiedadesDisponibles();
    }

    @GetMapping("/cuartos/{cuartos}")
    public List<DTOPropiedad> obtenerPropiedadesPorCuartos(@PathVariable int cuartos) {
        return servicioPropiedad.obtenerPropiedadesPorCuartos(cuartos);
    }

    @GetMapping("/area/{area}")
    public List<DTOPropiedad> obtenerPropiedadesPorArea(@PathVariable float area) {
        return servicioPropiedad.obtenerPropiedadesPorArea(area);
    }

    @GetMapping("/capacidad/{capacidad}")
    public List<DTOPropiedad> obtenerPropiedadesPorCapacidad(@PathVariable int capacidad) {
        return servicioPropiedad.obtenerPropiedadesPorCapacidad(capacidad);
    }

    @PostMapping("/crearPropiedad")
    public DTOPropiedad crearPropiedad(@RequestBody DTOPropiedad propiedad) {
        return servicioPropiedad.crearPropiedad(propiedad);
    }

    @PutMapping("/actualizarPropiedad/{id}")
    public DTOPropiedad actualizarPropiedad(@PathVariable Long id, @RequestBody DTOPropiedad propiedad) {
        return servicioPropiedad.actualizarPropiedad(id, propiedad);
    }

    @DeleteMapping("/eliminarPropiedad/{id}")
    public void eliminarPropiedad(@PathVariable Long id) {
        servicioPropiedad.eliminarPropiedad(id);
    }

}