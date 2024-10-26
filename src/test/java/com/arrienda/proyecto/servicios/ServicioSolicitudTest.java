package com.arrienda.proyecto.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.modelos.Arrendatario;
import com.arrienda.proyecto.modelos.Propiedad;
import com.arrienda.proyecto.modelos.Solicitud;
import com.arrienda.proyecto.repositorios.RepositorioArrendatario;
import com.arrienda.proyecto.repositorios.RepositorioPropiedad;
import com.arrienda.proyecto.repositorios.RepositorioSolicitud;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ServicioSolicitudTest {

    @Autowired
    private ServicioSolicitud servicioSolicitud;

    @MockBean
    private RepositorioSolicitud repositorioSolicitud;

    @MockBean
    private RepositorioPropiedad repositorioPropiedad;

    @MockBean
    private RepositorioArrendatario repositorioArrendatario;

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
        dtoSolicitud.setArrendatarioId(1L);
        dtoSolicitud.setFechaLlegada(Date.valueOf("2025-06-16"));
        dtoSolicitud.setFechaPartida(Date.valueOf("2025-06-23"));

        // Configurar entidades simuladas
        Arrendatario arrendatario = new Arrendatario();
        arrendatario.setId(1L);
        arrendatario.setNombre("Juan Pérez");

        Propiedad propiedad = new Propiedad();
        propiedad.setId(100L);
        propiedad.setNombre("Santiago Bernabeu");
        propiedad.setArrendadorId(2L); // Asegurarse de que el arrendador no sea el mismo que el arrendatario
        propiedad.setDisponible(true);

        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setPropiedadId(100L);
        solicitud.setArrendatarioId(1L);
        solicitud.setFechaLlegada(Date.valueOf("2025-06-16"));
        solicitud.setFechaPartida(Date.valueOf("2025-06-23"));

        Solicitud savedSolicitud = new Solicitud();
        savedSolicitud.setId(1L);
        savedSolicitud.setPropiedadId(100L);
        savedSolicitud.setArrendatarioId(1L);
        savedSolicitud.setFechaLlegada(Date.valueOf("2025-06-16"));
        savedSolicitud.setFechaPartida(Date.valueOf("2025-06-23"));

        // Configurar los mock
        when(repositorioArrendatario.findById(1L)).thenReturn(Optional.of(arrendatario));
        when(repositorioPropiedad.findById(100L)).thenReturn(Optional.of(propiedad));
        when(repositorioSolicitud
                .existsByPropiedadIdAndAceptacionAndFechaLlegadaLessThanEqualAndFechaPartidaGreaterThanEqual(
                        100L, true, dtoSolicitud.getFechaPartida(), dtoSolicitud.getFechaLlegada()))
                .thenReturn(false);
        when(modelMapper.map(dtoSolicitud, Solicitud.class)).thenReturn(solicitud);
        when(repositorioSolicitud.save(solicitud)).thenReturn(savedSolicitud);
        when(modelMapper.map(savedSolicitud, DTOSolicitud.class)).thenReturn(dtoSolicitud);

        // Ejecutar el método de prueba
        DTOSolicitud result = servicioSolicitud.crearSolicitud(dtoSolicitud);

        // Verificaciones
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100L, result.getPropiedadId());
        assertEquals(1L, result.getArrendatarioId());

        verify(repositorioArrendatario, times(1)).findById(1L);
        verify(repositorioPropiedad, times(1)).findById(100L);
        verify(repositorioSolicitud, times(1))
                .existsByPropiedadIdAndAceptacionAndFechaLlegadaLessThanEqualAndFechaPartidaGreaterThanEqual(
                        100L, true, dtoSolicitud.getFechaPartida(), dtoSolicitud.getFechaLlegada());
        verify(modelMapper, times(1)).map(dtoSolicitud, Solicitud.class);
        verify(repositorioSolicitud, times(1)).save(solicitud);
        verify(modelMapper, times(1)).map(savedSolicitud, DTOSolicitud.class);
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
    public void testEliminarSolicitud_Existente() {
        // Configurar ID de la solicitud a eliminar
        Long solicitudId = 1L;

        // Configurar entidad simulada
        Solicitud solicitud = new Solicitud();
        solicitud.setId(solicitudId);

        // Configurar el mock para que encuentre la solicitud
        when(repositorioSolicitud.findById(solicitudId)).thenReturn(Optional.of(solicitud));

        // Ejecutar el método de prueba
        servicioSolicitud.eliminarSolicitud(solicitudId);

        // Verificar que el método deleteById se llame una vez con el ID correcto
        verify(repositorioSolicitud, times(1)).deleteById(solicitudId);
    }

    /*
     * @Test
     * public void testEliminarSolicitud_NoExistente() {
     * // Configurar ID de la solicitud a eliminar
     * Long solicitudId = 1L;
     * 
     * // Configurar el mock para que no encuentre la solicitud
     * when(repositorioSolicitud.findById(solicitudId)).thenReturn(Optional.empty())
     * ;
     * 
     * // Ejecutar el método de prueba y esperar que se lance una excepción
     * servicioSolicitud.eliminarSolicitud(solicitudId);
     * }
     */
}
