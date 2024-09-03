package com.arrienda.proyecto.servicios;

import com.arrienda.proyecto.dtos.DTOCalificacion;
import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.repositorios.RepositorioCalificacion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ServicioCalificacion.class)
public class ServicioCalificacionTest {

    @Autowired
    private ServicioCalificacion servicioCalificacion;

    @MockBean
    private RepositorioCalificacion repositorioCalificacion;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void testTraerCalificaciones() {
        Calificacion calificacion1 = new Calificacion();
        calificacion1.setId(1L);
        calificacion1.setCalificacion(5);

        Calificacion calificacion2 = new Calificacion();
        calificacion2.setId(2L);
        calificacion2.setCalificacion(4);

        DTOCalificacion dtoCalificacion1 = new DTOCalificacion();
        dtoCalificacion1.setId(1L);
        dtoCalificacion1.setCalificacion(5);

        DTOCalificacion dtoCalificacion2 = new DTOCalificacion();
        dtoCalificacion2.setId(2L);
        dtoCalificacion2.setCalificacion(4);

        when(repositorioCalificacion.findAll()).thenReturn(Arrays.asList(calificacion1, calificacion2));
        when(modelMapper.map(calificacion1, DTOCalificacion.class)).thenReturn(dtoCalificacion1);
        when(modelMapper.map(calificacion2, DTOCalificacion.class)).thenReturn(dtoCalificacion2);

        var result = servicioCalificacion.traerCalificaciones();

        assertEquals(2, result.size());
        assertEquals(dtoCalificacion1, result.get(0));
        assertEquals(dtoCalificacion2, result.get(1));
    }

    @Test
    void testTraerCalificacionPorId() {
        Calificacion calificacion = new Calificacion();
        calificacion.setId(1L);
        calificacion.setCalificacion(5);

        DTOCalificacion dtoCalificacion = new DTOCalificacion();
        dtoCalificacion.setId(1L);
        dtoCalificacion.setCalificacion(5);

        when(repositorioCalificacion.findById(anyLong())).thenReturn(Optional.of(calificacion));
        when(modelMapper.map(calificacion, DTOCalificacion.class)).thenReturn(dtoCalificacion);

        var result = servicioCalificacion.traerCalificacionPorId(1L);

        assertEquals(dtoCalificacion, result);
    }

    @Test
    void testGuardarCalificacion() {
        Calificacion calificacion = new Calificacion();
        calificacion.setId(1L);
        calificacion.setCalificacion(5);

        DTOCalificacion dtoCalificacion = new DTOCalificacion();
        dtoCalificacion.setId(1L);
        dtoCalificacion.setCalificacion(5);

        when(repositorioCalificacion.save(any(Calificacion.class))).thenReturn(calificacion);
        when(modelMapper.map(dtoCalificacion, Calificacion.class)).thenReturn(calificacion);
        when(modelMapper.map(calificacion, DTOCalificacion.class)).thenReturn(dtoCalificacion);

        var result = servicioCalificacion.guardarCalificacion(dtoCalificacion);

        assertEquals(dtoCalificacion, result);
    }

    @Test
    void testActualizarCalificacion() {
        Calificacion existingCalificacion = new Calificacion();
        existingCalificacion.setId(1L);
        existingCalificacion.setCalificacion(4);

        DTOCalificacion dtoCalificacion = new DTOCalificacion();
        dtoCalificacion.setId(1L);
        dtoCalificacion.setCalificacion(5);

        when(repositorioCalificacion.findById(anyLong())).thenReturn(Optional.of(existingCalificacion));
        when(repositorioCalificacion.save(any(Calificacion.class))).thenReturn(existingCalificacion);
        when(modelMapper.map(dtoCalificacion, Calificacion.class)).thenReturn(existingCalificacion);
        when(modelMapper.map(existingCalificacion, DTOCalificacion.class)).thenReturn(dtoCalificacion);

        var result = servicioCalificacion.actualizarCalificacion(1L, dtoCalificacion);

        assertEquals(dtoCalificacion, result);
    }

    @Test
    void testEliminarCalificacion() {
        Long calificacionId = 1L;

        Calificacion calificacion = new Calificacion();
        calificacion.setId(calificacionId);

        when(repositorioCalificacion.findById(calificacionId)).thenReturn(Optional.of(calificacion));

        servicioCalificacion.eliminarCalificacion(1L);

        verify(repositorioCalificacion, times(1)).deleteById(1L);
    }
}
