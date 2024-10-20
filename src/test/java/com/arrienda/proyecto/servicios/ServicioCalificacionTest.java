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
import java.util.Collections;
import java.util.List;
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
    private ServicioArrendador servicioArrendador;

    @MockBean
    private ServicioArrendatario servicioArrendatario;

    @MockBean
    private ServicioPropiedad servicioPropiedad;

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
    public void testEliminarCalificacionYActualizarPromedio() {
        Long calificacionId = 1L;

        Calificacion calificacion = new Calificacion();
        calificacion.setId(calificacionId);
        calificacion.setIdTipo(0);
        calificacion.setIdCalificado(1L);
        calificacion.setCalificacion(4);

        when(repositorioCalificacion.findById(calificacionId)).thenReturn(Optional.of(calificacion));
        doNothing().when(repositorioCalificacion).deleteById(calificacionId);

        servicioCalificacion.eliminarCalificacion(calificacionId);

        verify(repositorioCalificacion, times(1)).deleteById(calificacionId);
        verify(servicioArrendador).actualizarPromedioCalificacion(1L, 0.0f); 
    }

    @Test
    public void testActualizarPromedioTipoArrendador() {
        Calificacion calificacion = new Calificacion();
        calificacion.setIdTipo(0);
        calificacion.setIdCalificado(1L);

        Calificacion calificacion1 = new Calificacion();
        calificacion1.setCalificacion(4);

        Calificacion calificacion2 = new Calificacion();
        calificacion2.setCalificacion(5);

        List<Calificacion> calificaciones = Arrays.asList(calificacion1, calificacion2);

        when(repositorioCalificacion.findByIdCalificadoAndIdTipo(1L, 0)).thenReturn(calificaciones);

        servicioCalificacion.actualizarPromedio(calificacion);

        verify(servicioArrendador).actualizarPromedioCalificacion(1L, 4.5f);
    }

    @Test
    public void testActualizarPromedioTipoArrendatario() {
        Calificacion calificacion = new Calificacion();
        calificacion.setIdTipo(1);
        calificacion.setIdCalificado(2L);

        Calificacion calificacion1 = new Calificacion();
        calificacion1.setCalificacion(3);

        Calificacion calificacion2 = new Calificacion();
        calificacion2.setCalificacion(3);

        List<Calificacion> calificaciones = Arrays.asList(calificacion1, calificacion2);

        when(repositorioCalificacion.findByIdCalificadoAndIdTipo(2L, 1)).thenReturn(calificaciones);

        servicioCalificacion.actualizarPromedio(calificacion);

        verify(servicioArrendatario).actualizarPromedioCalificacion(2L, 3.0f);
    }
    
    @Test
    public void testActualizarPromedioTipoPropiedad() {
        Calificacion calificacion = new Calificacion();
        calificacion.setIdTipo(2);
        calificacion.setIdCalificado(3L);

        Calificacion calificacion1 = new Calificacion();
        calificacion1.setCalificacion(2);

        Calificacion calificacion2 = new Calificacion();
        calificacion2.setCalificacion(4);

        List<Calificacion> calificaciones = Arrays.asList(calificacion1, calificacion2);

        when(repositorioCalificacion.findByIdCalificadoAndIdTipo(3L, 2)).thenReturn(calificaciones);

        servicioCalificacion.actualizarPromedio(calificacion);

        verify(servicioPropiedad).actualizarPromedioCalificacion(3L, 3.0f);
    }

    @Test
    public void testActualizarPromedioEmptyList() {
        Calificacion calificacion = new Calificacion();
        calificacion.setIdTipo(0);
        calificacion.setIdCalificado(1L);

        when(repositorioCalificacion.findByIdCalificadoAndIdTipo(1L, 0)).thenReturn(Collections.emptyList());

        servicioCalificacion.actualizarPromedio(calificacion);

        verify(servicioArrendador).actualizarPromedioCalificacion(1L, 0.0f);
    }

    @Test
    public void testActualizarPromedioNullList() {
        Calificacion calificacion = new Calificacion();
        calificacion.setIdTipo(0);
        calificacion.setIdCalificado(1L);

        when(repositorioCalificacion.findByIdCalificadoAndIdTipo(1L, 0)).thenReturn(null);

        servicioCalificacion.actualizarPromedio(calificacion);

        verify(servicioArrendador).actualizarPromedioCalificacion(1L, 0.0f);
    }

    @Test
    public void testCalcularPromedioConCalificaciones() {
        Calificacion calificacion1 = new Calificacion();
        calificacion1.setCalificacion(4);

        Calificacion calificacion2 = new Calificacion();
        calificacion2.setCalificacion(5);

        List<Calificacion> calificaciones = Arrays.asList(calificacion1, calificacion2);

        float promedio = servicioCalificacion.calcularPromedio(calificaciones);

        assertEquals(4.5f, promedio);
    }

}
