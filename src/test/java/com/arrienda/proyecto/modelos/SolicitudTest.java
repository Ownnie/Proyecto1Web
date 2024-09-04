package com.arrienda.proyecto.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolicitudTest {

    private Solicitud solicitud;

    @BeforeEach
    public void setUp() {
        solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setFechaLlegada(Date.valueOf("2023-01-01"));
        solicitud.setFechaPartida(Date.valueOf("2023-01-10"));
        solicitud.setAceptacion(true);
        solicitud.setCantidadPersonas(4);
        solicitud.setStatus(0);
        solicitud.setPropiedadId(2L);
        solicitud.setArrendatarioId(3L);
    }

    @Test
    public void testSolicitudCreation() {
        assertNotNull(solicitud);
        assertEquals(1L, solicitud.getId());
        assertEquals(Date.valueOf("2023-01-01"), solicitud.getFechaLlegada());
        assertEquals(Date.valueOf("2023-01-10"), solicitud.getFechaPartida());
        assertEquals(true, solicitud.isAceptacion());
        assertEquals(4, solicitud.getCantidadPersonas());
        assertEquals(0, solicitud.getStatus());
        assertEquals(2L, solicitud.getPropiedadId());
        assertEquals(3L, solicitud.getArrendatarioId());
    }

    @Test
    public void testSetAndGetFechaLlegada() {
        solicitud.setFechaLlegada(Date.valueOf("2023-02-01"));
        assertEquals(Date.valueOf("2023-02-01"), solicitud.getFechaLlegada());
    }

    @Test
    public void testSetAndGetFechaPartida() {
        solicitud.setFechaPartida(Date.valueOf("2023-02-10"));
        assertEquals(Date.valueOf("2023-02-10"), solicitud.getFechaPartida());
    }

    @Test
    public void testSetAndGetAceptacion() {
        solicitud.setAceptacion(false);
        assertEquals(false, solicitud.isAceptacion());
    }

    @Test
    public void testSetAndGetCantidadPersonas() {
        solicitud.setCantidadPersonas(5);
        assertEquals(5, solicitud.getCantidadPersonas());
    }

    @Test
    public void testSetAndGetStatus() {
        solicitud.setStatus(1);
        assertEquals(1, solicitud.getStatus());
    }

    @Test
    public void testSetAndGetPropiedadId() {
        solicitud.setPropiedadId(4L);
        assertEquals(4L, solicitud.getPropiedadId());
    }

    @Test
    public void testSetAndGetArrendatarioId() {
        solicitud.setArrendatarioId(5L);
        assertEquals(5L, solicitud.getArrendatarioId());
    }
}
