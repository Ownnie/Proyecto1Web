package com.arrienda.proyecto.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.arrienda.proyecto.dtos.DTOSolicitud;
import com.arrienda.proyecto.modelos.Solicitud;
import com.arrienda.proyecto.repositorios.RepositorioSolicitud;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ServicioSolicitudTest {

    @Autowired
    private ServicioSolicitud servicioSolicitud;

    @MockBean
    private RepositorioSolicitud repositorioSolicitud;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testGetAllSolicitudes() {
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);

        DTOSolicitud dtoSolicitud = new DTOSolicitud();
        dtoSolicitud.setId(1L);

        when(repositorioSolicitud.findAll()).thenReturn(Collections.singletonList(solicitud));
        when(modelMapper.map(solicitud, DTOSolicitud.class)).thenReturn(dtoSolicitud);

        List<DTOSolicitud> result = servicioSolicitud.getAllSolicitudes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        verify(repositorioSolicitud, times(1)).findAll();
    }

    @Test
    public void testGetSolicitud() {
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);

        DTOSolicitud dtoSolicitud = new DTOSolicitud();
        dtoSolicitud.setId(1L);

        when(repositorioSolicitud.findById(1L)).thenReturn(Optional.of(solicitud));
        when(modelMapper.map(solicitud, DTOSolicitud.class)).thenReturn(dtoSolicitud);

        DTOSolicitud result = servicioSolicitud.getSolicitud(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repositorioSolicitud, times(1)).findById(1L);
    }

    @Test
    public void testGetSolicitudesByEstado() {
        Solicitud solicitud = new Solicitud();
        solicitud.setStatus(1);

        DTOSolicitud dtoSolicitud = new DTOSolicitud();
        dtoSolicitud.setStatus(1);

        when(repositorioSolicitud.findByStatus(1)).thenReturn(Collections.singletonList(solicitud));
        when(modelMapper.map(solicitud, DTOSolicitud.class)).thenReturn(dtoSolicitud);

        List<DTOSolicitud> result = servicioSolicitud.getSolicitudesByEstado(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getStatus());

        verify(repositorioSolicitud, times(1)).findByStatus(1);
    }

    @Test
    public void testGetSolicitudesByArrendatarioId() {
        Solicitud solicitud = new Solicitud();
        solicitud.setArrendatarioId(1L);

        DTOSolicitud dtoSolicitud = new DTOSolicitud();
        dtoSolicitud.setArrendatarioId(1L);

        when(repositorioSolicitud.findByArrendatarioId(1L)).thenReturn(Collections.singletonList(solicitud));
        when(modelMapper.map(solicitud, DTOSolicitud.class)).thenReturn(dtoSolicitud);

        List<DTOSolicitud> result = servicioSolicitud.getSolicitudesByArrendatarioId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getArrendatarioId());

        verify(repositorioSolicitud, times(1)).findByArrendatarioId(1L);
    }

    @Test
    public void testGetSolicitudesByPropiedadId() {
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setPropiedadId(100L);

        DTOSolicitud dtoSolicitud = new DTOSolicitud();
        dtoSolicitud.setId(1L);
        dtoSolicitud.setPropiedadId(100L);

        when(repositorioSolicitud.findByPropiedadId(100L)).thenReturn(Collections.singletonList(solicitud));
        when(modelMapper.map(solicitud, DTOSolicitud.class)).thenReturn(dtoSolicitud);

        List<DTOSolicitud> result = servicioSolicitud.getSolicitudesByPropiedadId(100L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getPropiedadId());

        verify(repositorioSolicitud, times(1)).findByPropiedadId(100L);
        verify(modelMapper, times(1)).map(solicitud, DTOSolicitud.class);
    }

    @Test
    public void testCrearSolicitud() {
        DTOSolicitud dtoSolicitud = new DTOSolicitud();
        dtoSolicitud.setId(1L);
        dtoSolicitud.setPropiedadId(100L);

        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setPropiedadId(100L);

        when(modelMapper.map(dtoSolicitud, Solicitud.class)).thenReturn(solicitud);
        when(repositorioSolicitud.save(solicitud)).thenReturn(solicitud);
        when(modelMapper.map(solicitud, DTOSolicitud.class)).thenReturn(dtoSolicitud);

        DTOSolicitud result = servicioSolicitud.crearSolicitud(dtoSolicitud);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100L, result.getPropiedadId());

        verify(modelMapper, times(1)).map(dtoSolicitud, Solicitud.class);
        verify(repositorioSolicitud, times(1)).save(solicitud);
        verify(modelMapper, times(1)).map(solicitud, DTOSolicitud.class);
    }

    @Test
    public void testActualizarSolicitud() {
        DTOSolicitud dtoSolicitud = new DTOSolicitud();
        dtoSolicitud.setId(1L);
        dtoSolicitud.setPropiedadId(100L);

        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setPropiedadId(100L);

        when(repositorioSolicitud.findById(1L)).thenReturn(Optional.of(solicitud));
        when(repositorioSolicitud.save(solicitud)).thenReturn(solicitud);
        when(modelMapper.map(solicitud, DTOSolicitud.class)).thenReturn(dtoSolicitud);

        DTOSolicitud result = servicioSolicitud.actualizarSolicitud(1L, dtoSolicitud);

        assertNotNull(result);
        assertEquals(100L, result.getPropiedadId());

        verify(repositorioSolicitud, times(1)).findById(1L);
        verify(repositorioSolicitud, times(1)).save(solicitud);
        verify(modelMapper, times(1)).map(solicitud, DTOSolicitud.class);
    }

    @Test
    public void testEliminarSolicitud() {
        doNothing().when(repositorioSolicitud).deleteById(1L);

        servicioSolicitud.eliminarSolicitud(1L);

        verify(repositorioSolicitud, times(1)).deleteById(1L);
    }
}
