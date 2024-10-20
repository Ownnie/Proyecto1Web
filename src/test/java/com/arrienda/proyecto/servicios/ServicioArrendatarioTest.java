package com.arrienda.proyecto.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.modelos.*;
import com.arrienda.proyecto.repositorios.*;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ServicioArrendatarioTest {

    @Autowired
    private ServicioArrendatario servicioArrendatario;

    @MockBean
    private RepositorioArrendatario repositorioArrendatario;

    @MockBean
    private RepositorioSolicitud repositorioSolicitud;

    @MockBean
    private RepositorioCalificacion repositorioCalificacion;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testGetAllArrendatarios() {
        // Configuración de datos de prueba
        Arrendatario arrendatario1 = new Arrendatario();
        arrendatario1.setId(1L);
        arrendatario1.setNombre("Pedro");
        arrendatario1.setCorreo("pedro@example.com");
        Arrendatario arrendatario2 = new Arrendatario();
        arrendatario2.setId(2L);
        arrendatario1.setNombre("Daniel");
        arrendatario1.setCorreo("daniel@example.com");

        // Crea DTOs correspondientes a los arrendatarios
        DTOArrendatario dtoArrendatario1 = new DTOArrendatario();
        dtoArrendatario1.setId(1L);
        DTOArrendatario dtoArrendatario2 = new DTOArrendatario();
        dtoArrendatario2.setId(2L);

        when(repositorioArrendatario.findAll()).thenReturn(Arrays.asList(arrendatario1, arrendatario2));
        when(modelMapper.map(arrendatario1, DTOArrendatario.class)).thenReturn(dtoArrendatario1);
        when(modelMapper.map(arrendatario2, DTOArrendatario.class)).thenReturn(dtoArrendatario2);

        List<DTOArrendatario> resultado = servicioArrendatario.getAllArrendatarios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repositorioArrendatario, times(1)).findAll();
        verify(modelMapper, times(1)).map(arrendatario1, DTOArrendatario.class);
        verify(modelMapper, times(1)).map(arrendatario2, DTOArrendatario.class);
    }

    @Test
    public void testGetArrendatario() {
        Long id = 1L;
        Arrendatario arrendatario = new Arrendatario();
        arrendatario.setId(id);

        DTOArrendatario dtoArrendatario = new DTOArrendatario();
        dtoArrendatario.setId(id);

        when(repositorioArrendatario.findById(id)).thenReturn(Optional.of(arrendatario));
        when(modelMapper.map(arrendatario, DTOArrendatario.class)).thenReturn(dtoArrendatario);

        DTOArrendatario resultado = servicioArrendatario.getArrendatario(id);

        assertNotNull(resultado);
        verify(repositorioArrendatario, times(1)).findById(id);
    }

    @Test
    public void testGetArrendatarioNotFound() {
        Long id = 1L;

        when(repositorioArrendatario.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> servicioArrendatario.getArrendatario(id));

        verify(repositorioArrendatario, times(1)).findById(id);
    }

    @Test
    public void testCreateArrendatario() {
        DTOArrendatario dtoArrendatario = new DTOArrendatario();
        dtoArrendatario.setUsuario("nuevoUsuario");

        Arrendatario arrendatario = new Arrendatario();

        when(repositorioArrendatario.existsByUsuario(dtoArrendatario.getUsuario())).thenReturn(false);
        when(modelMapper.map(dtoArrendatario, Arrendatario.class)).thenReturn(arrendatario);
        when(repositorioArrendatario.save(arrendatario)).thenReturn(arrendatario);
        when(modelMapper.map(arrendatario, DTOArrendatario.class)).thenReturn(dtoArrendatario);

        DTOArrendatario resultado = servicioArrendatario.createArrendatario(dtoArrendatario);

        assertNotNull(resultado);
        verify(repositorioArrendatario, times(1)).existsByUsuario(dtoArrendatario.getUsuario());
        verify(repositorioArrendatario, times(1)).save(arrendatario);
    }

    @Test
    public void testCreateArrendatarioUsuarioExistente() {
        DTOArrendatario dtoArrendatario = new DTOArrendatario();
        dtoArrendatario.setUsuario("usuarioExistente");

        when(repositorioArrendatario.existsByUsuario(dtoArrendatario.getUsuario())).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            servicioArrendatario.createArrendatario(dtoArrendatario);
        });

        assertEquals("El arrendatario con este usuario ya existe.", thrown.getMessage());
        verify(repositorioArrendatario, times(1)).existsByUsuario(dtoArrendatario.getUsuario());
        verify(repositorioArrendatario, never()).save(any(Arrendatario.class));
    }

    @Test
    public void testUpdateArrendatario() {
        Long id = 1L;
        DTOArrendatario dtoArrendatario = new DTOArrendatario();
        dtoArrendatario.setNombre("Nombre Actualizado");

        Arrendatario arrendatario = new Arrendatario();

        when(repositorioArrendatario.findById(id)).thenReturn(Optional.of(arrendatario));
        when(repositorioArrendatario.save(arrendatario)).thenReturn(arrendatario);
        when(modelMapper.map(dtoArrendatario, Arrendatario.class)).thenReturn(arrendatario);
        when(modelMapper.map(arrendatario, DTOArrendatario.class)).thenReturn(dtoArrendatario);

        DTOArrendatario resultado = servicioArrendatario.updateArrendatario(id, dtoArrendatario);

        assertNotNull(resultado);
        verify(repositorioArrendatario, times(1)).findById(id);
        verify(repositorioArrendatario, times(1)).save(arrendatario);
    }

    @Test
    public void testEliminarArrendatario() {
        Long id = 1L;
        Arrendatario arrendatario = new Arrendatario();

        when(repositorioArrendatario.findById(id)).thenReturn(Optional.of(arrendatario));

        servicioArrendatario.eliminarArrendatario(id);

        verify(repositorioArrendatario, times(1)).findById(id);
        verify(repositorioArrendatario, times(1)).delete(arrendatario);
    }
}
