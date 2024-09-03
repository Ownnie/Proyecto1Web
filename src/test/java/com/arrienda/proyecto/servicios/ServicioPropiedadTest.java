package com.arrienda.proyecto.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.arrienda.proyecto.dtos.DTOPropiedad;
import com.arrienda.proyecto.dtos.DTOSolicitud;
import com.arrienda.proyecto.dtos.DTOCalificacion;
import com.arrienda.proyecto.modelos.Propiedad;
import com.arrienda.proyecto.modelos.Solicitud;
import com.arrienda.proyecto.modelos.Arrendador;
import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.repositorios.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ServicioPropiedadTest {

    @Autowired
    private ServicioPropiedad servicioPropiedad;

    @MockBean
    private RepositorioPropiedad repositorioPropiedad;

    @MockBean
    private RepositorioSolicitud repositorioSolicitud;

    @MockBean
    private RepositorioCalificacion repositorioCalificacion;

    @MockBean
    private RepositorioArrendador repositorioArrendador;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testTraerPropiedades() {
        // Create mock data
        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setNombre("Casa de Campo");

        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setPropiedadId(1L);

        Calificacion calificacion = new Calificacion();
        calificacion.setId(1L);
        calificacion.setIdCalificado(1L);
        calificacion.setIdTipo(2);

        List<Propiedad> propiedades = Arrays.asList(propiedad);
        List<Solicitud> solicitudes = Arrays.asList(solicitud);
        List<Calificacion> calificaciones = Arrays.asList(calificacion);

        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setNombre("Casa de Campo");

        DTOSolicitud dtoSolicitud = new DTOSolicitud();
        dtoSolicitud.setId(1L);

        DTOCalificacion dtoCalificacion = new DTOCalificacion();
        dtoCalificacion.setId(1L);

        // Set up mock behavior
        when(repositorioPropiedad.findAll()).thenReturn(propiedades);
        when(repositorioSolicitud.findByPropiedadId(1L)).thenReturn(solicitudes);
        when(repositorioCalificacion.findByIdCalificadoAndIdTipo(1L, 2)).thenReturn(calificaciones);

        when(modelMapper.map(propiedad, DTOPropiedad.class)).thenReturn(dtoPropiedad);
        when(modelMapper.map(solicitud, DTOSolicitud.class)).thenReturn(dtoSolicitud);
        when(modelMapper.map(calificacion, DTOCalificacion.class)).thenReturn(dtoCalificacion);

        // Call the method under test
        List<DTOPropiedad> result = servicioPropiedad.traerPropiedades();

        // Verify the results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Casa de Campo", result.get(0).getNombre());

        assertNotNull(result.get(0).getSolicitudes());
        assertEquals(1, result.get(0).getSolicitudes().size());

        assertNotNull(result.get(0).getCalificaciones());
        assertEquals(1, result.get(0).getCalificaciones().size());

        // Verify interactions with mocks
        verify(repositorioPropiedad, times(1)).findAll();
        verify(repositorioSolicitud, times(1)).findByPropiedadId(1L);
        verify(repositorioCalificacion, times(1)).findByIdCalificadoAndIdTipo(1L, 2);
    }

    @Test
    public void testObtenerPropiedad() {
        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setNombre("Casa de Campo");

        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setNombre("Casa de Campo");

        when(repositorioPropiedad.findById(1L)).thenReturn(Optional.of(propiedad));
        when(modelMapper.map(propiedad, DTOPropiedad.class)).thenReturn(dtoPropiedad);

        DTOPropiedad result = servicioPropiedad.obtenerPropiedad(1L);

        assertNotNull(result);
        assertEquals("Casa de Campo", result.getNombre());

        verify(repositorioPropiedad, times(1)).findById(1L);
    }

    @Test
    public void testObtenerPropiedadesPorCamas() {
        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setCamas(3);

        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setCamas(3);

        when(repositorioPropiedad.findAll()).thenReturn(Collections.singletonList(propiedad));
        when(modelMapper.map(propiedad, DTOPropiedad.class)).thenReturn(dtoPropiedad);

        List<DTOPropiedad> result = servicioPropiedad.obtenerPropiedadesPorCamas(3);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getCamas());

        verify(repositorioPropiedad, times(1)).findAll();
    }

    @Test
    public void testObtenerPropiedadesDisponibles() {
        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setDisponible(true);

        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setDisponible(true);

        when(repositorioPropiedad.findAll()).thenReturn(Collections.singletonList(propiedad));
        when(modelMapper.map(propiedad, DTOPropiedad.class)).thenReturn(dtoPropiedad);

        List<DTOPropiedad> result = servicioPropiedad.obtenerPropiedadesDisponibles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isDisponible());

        verify(repositorioPropiedad, times(1)).findAll();
    }

    @Test
    public void testObtenerPropiedadesPorCuartos() {
        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setCuartos(2);

        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setCuartos(2);

        when(repositorioPropiedad.findAll()).thenReturn(Collections.singletonList(propiedad));
        when(modelMapper.map(propiedad, DTOPropiedad.class)).thenReturn(dtoPropiedad);

        List<DTOPropiedad> result = servicioPropiedad.obtenerPropiedadesPorCuartos(2);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getCuartos());

        verify(repositorioPropiedad, times(1)).findAll();
    }

    @Test
    public void testObtenerPropiedadesPorArea() {
        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setArea(100.0f);

        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setArea(100.0f);

        when(repositorioPropiedad.findAll()).thenReturn(Collections.singletonList(propiedad));
        when(modelMapper.map(propiedad, DTOPropiedad.class)).thenReturn(dtoPropiedad);

        List<DTOPropiedad> result = servicioPropiedad.obtenerPropiedadesPorArea(100.0f);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100.0f, result.get(0).getArea());

        verify(repositorioPropiedad, times(1)).findAll();
    }

    @Test
    public void testObtenerPropiedadesPorCapacidad() {
        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setCapacidad(4);

        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setCapacidad(4);

        when(repositorioPropiedad.findAll()).thenReturn(Collections.singletonList(propiedad));
        when(modelMapper.map(propiedad, DTOPropiedad.class)).thenReturn(dtoPropiedad);

        List<DTOPropiedad> result = servicioPropiedad.obtenerPropiedadesPorCapacidad(4);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getCapacidad());

        verify(repositorioPropiedad, times(1)).findAll();
    }

    @Test
    public void testGetPropiedades() {
        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setArrendadorId(1L);

        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setArrendadorId(1L);

        when(repositorioPropiedad.findAll()).thenReturn(Collections.singletonList(propiedad));
        when(modelMapper.map(propiedad, DTOPropiedad.class)).thenReturn(dtoPropiedad);

        List<DTOPropiedad> result = servicioPropiedad.getPropiedades(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getArrendadorId());

        verify(repositorioPropiedad, times(1)).findAll();
    }

    @Test
    public void testCrearPropiedad() {
        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setNombre("Casa de Campo");
        dtoPropiedad.setArrendadorId(1L);

        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setNombre("Casa de Campo");
        propiedad.setArrendadorId(1L);

        when(repositorioArrendador.findById(1L)).thenReturn(Optional.of(new Arrendador()));
        when(repositorioPropiedad.save(any(Propiedad.class))).thenReturn(propiedad);
        when(modelMapper.map(any(Propiedad.class), eq(DTOPropiedad.class))).thenReturn(dtoPropiedad);

        DTOPropiedad result = servicioPropiedad.crearPropiedad(dtoPropiedad);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Casa de Campo", result.getNombre());

        verify(repositorioArrendador, times(1)).findById(1L);
        verify(repositorioPropiedad, times(1)).save(any(Propiedad.class));
    }

    @Test
    public void testActualizarPropiedad() {
        DTOPropiedad dtoPropiedad = new DTOPropiedad();
        dtoPropiedad.setId(1L);
        dtoPropiedad.setNombre("Casa de Campo");

        Propiedad propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setNombre("Casa de Campo");

        when(repositorioPropiedad.findById(1L)).thenReturn(Optional.of(propiedad));
        when(repositorioPropiedad.save(any(Propiedad.class))).thenReturn(propiedad);
        when(modelMapper.map(any(Propiedad.class), eq(DTOPropiedad.class))).thenReturn(dtoPropiedad);

        DTOPropiedad result = servicioPropiedad.actualizarPropiedad(1L, dtoPropiedad);

        assertNotNull(result);
        assertEquals("Casa de Campo", result.getNombre());

        verify(repositorioPropiedad, times(1)).findById(1L);
        verify(repositorioPropiedad, times(1)).save(any(Propiedad.class));
    }

    @Test
    public void testEliminarPropiedad() {
        Long propiedadId = 1L;
        Propiedad propiedad = new Propiedad();
        propiedad.setId(propiedadId);
        when(repositorioPropiedad.findById(propiedadId)).thenReturn(Optional.of(propiedad));

        servicioPropiedad.eliminarPropiedad(1L);

        verify(repositorioPropiedad, times(1)).deleteById(1L);
    }
}
