package com.arrienda.proyecto.controladores;

import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.servicios.ServicioCalificacion;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ControllerCalificacion.class)
public class ControllerCalificacionTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioCalificacion servicioCalificacion;

    @Test
    public void testGetAllCalificaciones() throws Exception {
        DTOCalificacion calificacion1 = new DTOCalificacion();
        calificacion1.setId(3L);
        calificacion1.setCalificacion(5.0f);
        calificacion1.setComentario("le fue bien");
        calificacion1.setStatus(0);
        calificacion1.setIdTipo(1);
        calificacion1.setIdCalificado(10L);

        DTOCalificacion calificacion2 = new DTOCalificacion();
        calificacion2.setId(4L);
        calificacion2.setCalificacion(4.0f);
        calificacion2.setComentario("tranqui funki");
        calificacion2.setStatus(0);
        calificacion2.setIdTipo(2);
        calificacion2.setIdCalificado(20L);

        List<DTOCalificacion> calificaciones = Arrays.asList(calificacion1, calificacion2);

        when(servicioCalificacion.traerCalificaciones()).thenReturn(calificaciones);

        mockMvc.perform(MockMvcRequestBuilders.get("/calificacion/calificaciones")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[{\"id\": 3, \"calificacion\": 5.0, \"comentario\": \"le fue bien\", \"status\": 0, \"idTipo\": 1, \"idCalificado\": 10}, "
                                +
                                "{\"id\": 4, \"calificacion\": 4.0, \"comentario\": \"tranqui funki\", \"status\": 0, \"idTipo\": 2, \"idCalificado\": 20}]"));
    }

    @Test
    public void testGetCalificacionById() throws Exception {
        Long id = 1L;
        DTOCalificacion calificacion = new DTOCalificacion();
        calificacion.setId(id);
        calificacion.setCalificacion(5.0f);
        calificacion.setComentario("le fue bien");
        calificacion.setStatus(0);
        calificacion.setIdTipo(1);
        calificacion.setIdCalificado(10);

        when(servicioCalificacion.traerCalificacionPorId(id)).thenReturn(calificacion);

        mockMvc.perform(MockMvcRequestBuilders.get("/calificacion/calificaciones/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "{\"id\": 1, \"calificacion\": 5.0, \"comentario\": \"le fue bien\", \"status\": 0, \"idTipo\": 1, \"idCalificado\": 10}"));
    }

    @Test
    public void testgetCalificacionesArrendador() throws Exception {
        DTOCalificacion calificacion1 = new DTOCalificacion();
        calificacion1.setId(7L);
        calificacion1.setCalificacion(5f);
        calificacion1.setComentario("Excelente arrendador");
        calificacion1.setIdCalificado(1L);

        DTOCalificacion calificacion2 = new DTOCalificacion();
        calificacion2.setId(8L);
        calificacion2.setCalificacion(4f);
        calificacion2.setComentario("Buen trato, pero algunos detalles");
        calificacion2.setIdCalificado(1L);

        List<DTOCalificacion> calificacionesArrendador = Arrays.asList(calificacion1, calificacion2);

        when(servicioCalificacion.getCalificaciones(1L, 0)).thenReturn(calificacionesArrendador);

        mockMvc.perform(MockMvcRequestBuilders.get("/calificacion/arrendador/1/calificaciones")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[{\"id\":7,\"calificacion\":5.0,\"comentario\":\"Excelente arrendador\",\"idCalificado\":1}," +
                                "{\"id\":8,\"calificacion\":4.0,\"comentario\":\"Buen trato, pero algunos detalles\",\"idCalificado\":1}]"));
    }

    @Test
    public void testgetCalificacionesArrendatario() throws Exception {
        DTOCalificacion calificacion1 = new DTOCalificacion();
        calificacion1.setId(7L);
        calificacion1.setCalificacion(5f);
        calificacion1.setComentario("Excelente arrendador");
        calificacion1.setIdCalificado(5L);

        DTOCalificacion calificacion2 = new DTOCalificacion();
        calificacion2.setId(8L);
        calificacion2.setCalificacion(4f);
        calificacion2.setComentario("Buen trato, pero algunos detalles");
        calificacion2.setIdCalificado(5L);

        List<DTOCalificacion> calificacionesArrendatario = Arrays.asList(calificacion1, calificacion2);

        when(servicioCalificacion.getCalificaciones(1L, 1)).thenReturn(calificacionesArrendatario);

        mockMvc.perform(MockMvcRequestBuilders.get("/calificacion/arrendatario/1/calificaciones")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[{\"id\":7,\"calificacion\":5.0,\"comentario\":\"Excelente arrendador\",\"idCalificado\":5}," +
                                "{\"id\":8,\"calificacion\":4.0,\"comentario\":\"Buen trato, pero algunos detalles\",\"idCalificado\":5}]"));
    }

    @Test
    public void testgetCalificacionesPropiedad() throws Exception {
        DTOCalificacion calificacion1 = new DTOCalificacion();
        calificacion1.setId(11L);
        calificacion1.setCalificacion(5.0f);
        calificacion1.setComentario("Propiedad en excelente estado");
        calificacion1.setIdCalificado(1L);

        DTOCalificacion calificacion2 = new DTOCalificacion();
        calificacion2.setId(12L);
        calificacion2.setCalificacion(4.0f);
        calificacion2.setComentario("Buena propiedad, pero falta mantenimiento");
        calificacion2.setIdCalificado(1L);

        List<DTOCalificacion> calificacionesPropiedad = Arrays.asList(calificacion1, calificacion2);

        when(servicioCalificacion.getCalificaciones(1L, 2)).thenReturn(calificacionesPropiedad);

        mockMvc.perform(MockMvcRequestBuilders.get("/calificacion/propiedad/1/calificaciones")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[{\"id\":11,\"calificacion\":5.0,\"comentario\":\"Propiedad en excelente estado\",\"idCalificado\":1},"
                                +
                                "{\"id\":12,\"calificacion\":4.0,\"comentario\":\"Buena propiedad, pero falta mantenimiento\",\"idCalificado\":1}]"));
    }

    @Test
    public void testcrearCalificacion() throws Exception {
        String calificacionJson = "{\"id\":5,\"calificacion\":4.0,\"comentario\":\"creando ando\",\"status\":1,\"idTipo\":1,\"idCalificado\":1}";
        DTOCalificacion calificacion = new DTOCalificacion();
        calificacion.setId(5L);
        calificacion.setCalificacion(4.0f);
        calificacion.setComentario("creando ando");
        calificacion.setStatus(1);
        calificacion.setIdTipo(1);
        calificacion.setIdCalificado(1L);

        when(servicioCalificacion.guardarCalificacion(any(DTOCalificacion.class))).thenReturn(calificacion);

        mockMvc.perform(MockMvcRequestBuilders.post("/calificacion/crearCalificacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(calificacionJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "{\"id\":5,\"calificacion\":4.0,\"comentario\":\"creando ando\",\"status\":1,\"idTipo\":1,\"idCalificado\":1}"));
    }

    @Test
    public void testActualizarCalificacion() throws Exception {
        Long id = 1L;
        String calificacionJson = "{\"id\":1,\"calificacion\":3.0,\"comentario\":\"Actualizanding\",\"status\":1,\"idTipo\":2,\"idCalificado\":1}";
        DTOCalificacion calificacion = new DTOCalificacion();
        calificacion.setId(1L);
        calificacion.setCalificacion(3.0f);
        calificacion.setComentario("Actualizanding");
        calificacion.setStatus(1);
        calificacion.setIdTipo(2);
        calificacion.setIdCalificado(1L);

        when(servicioCalificacion.actualizarCalificacion(eq(id), any(DTOCalificacion.class))).thenReturn(calificacion);

        mockMvc.perform(MockMvcRequestBuilders.put("/calificacion/actualizarCalificacion/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(calificacionJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(calificacionJson));

        verify(servicioCalificacion, times(1)).actualizarCalificacion(eq(id), any(DTOCalificacion.class));
    }

    @Test
    public void testEliminarCalificacion() throws Exception {
        Long id = 1L;
        /*
         * when(servicioCalificacion.eliminarCalificacion(id))
         * .thenReturn(ResponseEntity.ok("Calificación eliminada con éxito."));
         */

        mockMvc.perform(MockMvcRequestBuilders.delete("/calificacion/eliminarCalificaciones/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Calificación eliminada con éxito."));

        verify(servicioCalificacion, times(1)).eliminarCalificacion(id);
    }

    @Test
    public void testEliminarCalificacionNotFound() throws Exception {
        Long id = 1L;
        doThrow(new EntityNotFoundException("Calificación no encontrada")).when(servicioCalificacion)
                .eliminarCalificacion(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/calificacion/eliminarCalificaciones/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Calificación no encontrada."));
    }

    @Test
    public void testEliminarCalificacionError() throws Exception {
        Long id = 1L;
        doThrow(new RuntimeException("Error interno del servidor")).when(servicioCalificacion)
                .eliminarCalificacion(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/calificacion/eliminarCalificaciones/{id}", id))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error al eliminar la calificación."));
    }

}
