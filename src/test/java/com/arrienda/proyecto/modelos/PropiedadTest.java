package com.arrienda.proyecto.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PropiedadTest {

    private Propiedad propiedad;

    @BeforeEach
    public void setUp() {
        propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setNombre("Casa de Playa");
        propiedad.setUbicacion("Playa del Carmen");
        propiedad.setParqueadero(true);
        propiedad.setPiscina(true);
        propiedad.setCuartos(3);
        propiedad.setCamas(5);
        propiedad.setArea(120.5f);
        propiedad.setCapacidad(8);
        propiedad.setDisponible(true);
        propiedad.setPrecioXnoche(1500L);
        propiedad.setStatus(0);
        propiedad.setArrendadorId(2L);
        propiedad.setCalificaciones(new ArrayList<>());
        propiedad.setSolicitudes(new ArrayList<>());
        propiedad.setCalificacionPromedio(0.0f);
    }

    @Test
    public void testPropiedadCreation() {
        assertNotNull(propiedad);
        assertEquals(1L, propiedad.getId());
        assertEquals("Casa de Playa", propiedad.getNombre());
        assertEquals("Playa del Carmen", propiedad.getUbicacion());
        assertEquals(true, propiedad.isParqueadero());
        assertEquals(true, propiedad.isPiscina());
        assertEquals(3, propiedad.getCuartos());
        assertEquals(5, propiedad.getCamas());
        assertEquals(120.5f, propiedad.getArea());
        assertEquals(8, propiedad.getCapacidad());
        assertEquals(true, propiedad.isDisponible());
        assertEquals(1500L, propiedad.getPrecioXnoche());
        assertEquals(0, propiedad.getStatus());
        assertEquals(2L, propiedad.getArrendadorId());
        assertNotNull(propiedad.getCalificaciones());
        assertNotNull(propiedad.getSolicitudes());
        assertEquals(0.0f,propiedad.getCalificacionPromedio());
    }

    @Test
    public void testSetAndGetNombre() {
        propiedad.setNombre("Casa de Montaña");
        assertEquals("Casa de Montaña", propiedad.getNombre());
    }

    @Test
    public void testSetAndGetUbicacion() {
        propiedad.setUbicacion("Montaña");
        assertEquals("Montaña", propiedad.getUbicacion());
    }

    @Test
    public void testSetAndGetParqueadero() {
        propiedad.setParqueadero(false);
        assertEquals(false, propiedad.isParqueadero());
    }

    @Test
    public void testSetAndGetPiscina() {
        propiedad.setPiscina(false);
        assertEquals(false, propiedad.isPiscina());
    }

    @Test
    public void testSetAndGetCuartos() {
        propiedad.setCuartos(4);
        assertEquals(4, propiedad.getCuartos());
    }

    @Test
    public void testSetAndGetCamas() {
        propiedad.setCamas(6);
        assertEquals(6, propiedad.getCamas());
    }

    @Test
    public void testSetAndGetArea() {
        propiedad.setArea(130.5f);
        assertEquals(130.5f, propiedad.getArea());
    }

    @Test
    public void testSetAndGetCapacidad() {
        propiedad.setCapacidad(10);
        assertEquals(10, propiedad.getCapacidad());
    }

    @Test
    public void testSetAndGetDisponible() {
        propiedad.setDisponible(false);
        assertEquals(false, propiedad.isDisponible());
    }

    @Test
    public void testSetAndGetPrecioXnoche() {
        propiedad.setPrecioXnoche(2000L);
        assertEquals(2000L, propiedad.getPrecioXnoche());
    }

    @Test
    public void testSetAndGetStatus() {
        propiedad.setStatus(1);
        assertEquals(1, propiedad.getStatus());
    }

    @Test
    public void testSetAndGetArrendadorId() {
        propiedad.setArrendadorId(3L);
        assertEquals(3L, propiedad.getArrendadorId());
    }

    @Test
    public void testSetAndGetCalificaciones() {
        List<Calificacion> calificaciones = new ArrayList<>();
        propiedad.setCalificaciones(calificaciones);
        assertEquals(calificaciones, propiedad.getCalificaciones());
    }

    @Test
    public void testSetAndGetSolicitudes() {
        List<Solicitud> solicitudes = new ArrayList<>();
        propiedad.setSolicitudes(solicitudes);
        assertEquals(solicitudes, propiedad.getSolicitudes());
    }
}
