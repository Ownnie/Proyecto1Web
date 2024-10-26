package com.arrienda.proyecto.controladores;

import jakarta.persistence.EntityNotFoundException;

import com.arrienda.proyecto.dtos.DTOSolicitud;
import com.arrienda.proyecto.servicios.ServicioSolicitud;

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
import java.sql.Date;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ControllerSolicitud.class)
public class ControllerSolicitudTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ServicioSolicitud servicioSolicitud;

        @Test
        public void testGetAllSolicitudes() throws Exception {
                DTOSolicitud solicitud = new DTOSolicitud();
                solicitud.setId(1L);
                solicitud.setFechaLlegada(Date.valueOf("2024-10-12"));
                solicitud.setFechaPartida(Date.valueOf("2024-10-20"));
                solicitud.setAceptacion(true);
                solicitud.setCantidadPersonas(4);
                solicitud.setStatus(1);
                solicitud.setPropiedadId(1L);
                solicitud.setArrendatarioId(1L);

                List<DTOSolicitud> solicitudes = Arrays.asList(solicitud);

                when(servicioSolicitud.getAllSolicitudes()).thenReturn(solicitudes);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/solicitud/solicitudes")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":4,\"status\":1,\"propiedadId\":1,\"arrendatarioId\":1}]"));
        }

        @Test
        public void testGetSolicitud() throws Exception {
                DTOSolicitud solicitud = new DTOSolicitud();
                solicitud.setId(1L);
                solicitud.setFechaLlegada(Date.valueOf("2024-10-12"));
                solicitud.setFechaPartida(Date.valueOf("2024-10-20"));
                solicitud.setAceptacion(true);
                solicitud.setCantidadPersonas(4);
                solicitud.setStatus(1);
                solicitud.setPropiedadId(1L);
                solicitud.setArrendatarioId(1L);

                when(servicioSolicitud.getSolicitud(1L)).thenReturn(solicitud);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/solicitud/solicitud/1")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":4,\"status\":1,\"propiedadId\":1,\"arrendatarioId\":1}"));

                verify(servicioSolicitud, times(1)).getSolicitud(1L);
        }

        @Test
        public void testGetSolicitudesByEstado() throws Exception {
                DTOSolicitud solicitud = new DTOSolicitud();
                solicitud.setId(1L);
                solicitud.setFechaLlegada(Date.valueOf("2024-10-12"));
                solicitud.setFechaPartida(Date.valueOf("2024-10-20"));
                solicitud.setAceptacion(true);
                solicitud.setCantidadPersonas(4);
                solicitud.setStatus(1);
                solicitud.setPropiedadId(1L);
                solicitud.setArrendatarioId(1L);

                List<DTOSolicitud> solicitudes = Arrays.asList(solicitud);

                when(servicioSolicitud.getSolicitudesByEstado(1)).thenReturn(solicitudes);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/solicitud/solicitudes/estado/1")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":4,\"status\":1,\"propiedadId\":1,\"arrendatarioId\":1}]"));

                verify(servicioSolicitud, times(1)).getSolicitudesByEstado(1);
        }

        @Test
        public void testGetSolicitudesByArrendatarioId() throws Exception {
                DTOSolicitud solicitud = new DTOSolicitud();
                solicitud.setId(1L);
                solicitud.setFechaLlegada(Date.valueOf("2024-10-12"));
                solicitud.setFechaPartida(Date.valueOf("2024-10-20"));
                solicitud.setAceptacion(true);
                solicitud.setCantidadPersonas(4);
                solicitud.setStatus(1);
                solicitud.setPropiedadId(1L);
                solicitud.setArrendatarioId(1L);

                List<DTOSolicitud> solicitudes = Arrays.asList(solicitud);

                when(servicioSolicitud.getSolicitudesByArrendatarioId(1L)).thenReturn(solicitudes);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/solicitud/solicitudes/arrendatario/1")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":4,\"status\":1,\"propiedadId\":1,\"arrendatarioId\":1}]"));

                verify(servicioSolicitud, times(1)).getSolicitudesByArrendatarioId(1L);
        }

        @Test
        public void testGetSolicitudesByPropiedadId() throws Exception {
                DTOSolicitud solicitud1 = new DTOSolicitud(
                                1L,
                                Date.valueOf("2024-10-12"),
                                Date.valueOf("2024-10-20"),
                                true,
                                3,
                                1,
                                1L,
                                1L);

                DTOSolicitud solicitud2 = new DTOSolicitud(
                                2L,
                                Date.valueOf("2024-11-12"),
                                Date.valueOf("2024-11-20"),
                                false,
                                2,
                                2,
                                2L,
                                2L);

                List<DTOSolicitud> solicitudes = Arrays.asList(solicitud1, solicitud2);

                when(servicioSolicitud.getSolicitudesByPropiedadId(1L)).thenReturn(solicitudes);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/solicitud/solicitudes/propiedad/1")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":3,\"status\":1,\"propiedadId\":1,\"arrendatarioId\":1},"
                                                                +
                                                                "{\"id\":2,\"fechaLlegada\":\"2024-11-12\",\"fechaPartida\":\"2024-11-20\",\"aceptacion\":false,\"cantidadPersonas\":2,\"status\":2,\"propiedadId\":2,\"arrendatarioId\":2}]"));

                verify(servicioSolicitud, times(1)).getSolicitudesByPropiedadId(1L);
        }

        @Test
        public void testCrearSolicitud() throws Exception {
                DTOSolicitud solicitud = new DTOSolicitud(
                                1L,
                                Date.valueOf("2024-11-12"),
                                Date.valueOf("2024-11-20"),
                                true,
                                3,
                                1,
                                1L,
                                1L);

                when(servicioSolicitud.crearSolicitud(any(DTOSolicitud.class))).thenReturn(solicitud);

                mockMvc.perform(MockMvcRequestBuilders.post("/api/solicitud/crearSolicitud")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"fechaLlegada\":\"" + solicitud.getFechaLlegada()
                                                + "\",\"fechaPartida\":\""
                                                + solicitud.getFechaPartida()
                                                + "\",\"aceptacion\":true,\"cantidadPersonas\":3,\"status\":1,\"propiedadId\":1,\"arrendatarioId\":1}"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "{\"id\":1,\"fechaLlegada\":\"" + solicitud.getFechaLlegada()
                                                                + "\",\"fechaPartida\":\""
                                                                + solicitud.getFechaPartida()
                                                                + "\",\"aceptacion\":true,\"cantidadPersonas\":3,\"status\":1,\"propiedadId\":1,\"arrendatarioId\":1}"));

                verify(servicioSolicitud, times(1)).crearSolicitud(any(DTOSolicitud.class));
        }

        @Test
        public void testActualizarSolicitud() throws Exception {
                DTOSolicitud solicitudActualizada = new DTOSolicitud(
                                1L,
                                Date.valueOf("2024-11-12"),
                                Date.valueOf("2024-11-20"),
                                false,
                                4,
                                2,
                                1L,
                                1L);

                when(servicioSolicitud.actualizarSolicitud(eq(1L), any(DTOSolicitud.class)))
                                .thenReturn(solicitudActualizada);

                mockMvc.perform(MockMvcRequestBuilders.put("/api/solicitud/actualizarSolicitud/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"fechaLlegada\":\"" + solicitudActualizada.getFechaLlegada()
                                                + "\",\"fechaPartida\":\"" + solicitudActualizada.getFechaPartida()
                                                + "\",\"aceptacion\":false,\"cantidadPersonas\":4,\"status\":2,\"propiedadId\":1,\"arrendatarioId\":1}"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "{\"id\":1,\"fechaLlegada\":\"" + solicitudActualizada.getFechaLlegada()
                                                                + "\",\"fechaPartida\":\""
                                                                + solicitudActualizada.getFechaPartida()
                                                                + "\",\"aceptacion\":false,\"cantidadPersonas\":4,\"status\":2,\"propiedadId\":1,\"arrendatarioId\":1}"));

                verify(servicioSolicitud, times(1)).actualizarSolicitud(eq(1L), any(DTOSolicitud.class));
        }

        @Test
        public void testEliminarSolicitud() throws Exception {
                Long id = 1L;
                /*
                 * when(servicioSolicitud.eliminarSolicitud(id))
                 * .thenReturn(ResponseEntity.ok("Solicitud eliminada con éxito."));
                 */

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/solicitud/eliminarSolicitud/{id}", id))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Solicitud eliminada con éxito."));

                verify(servicioSolicitud, times(1)).eliminarSolicitud(id);
        }

        @Test
        public void testEliminarSolicitudNotFound() throws Exception {
                Long id = 1L;
                doThrow(new EntityNotFoundException("Solicitud no encontrada")).when(servicioSolicitud)
                                .eliminarSolicitud(id);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/solicitud/eliminarSolicitud/{id}", id))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Solicitud no encontrada."));
        }

        @Test
        public void testEliminarSolicitudError() throws Exception {
                Long id = 1L;
                doThrow(new RuntimeException("Error interno del servidor")).when(servicioSolicitud)
                                .eliminarSolicitud(id);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/solicitud/eliminarSolicitud/{id}", id))
                                .andExpect(status().isInternalServerError())
                                .andExpect(content().string("Error al eliminar la solicitud."));
        }
}
