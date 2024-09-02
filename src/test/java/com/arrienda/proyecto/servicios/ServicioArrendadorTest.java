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

import com.arrienda.proyecto.dtos.DTOArrendador;
import com.arrienda.proyecto.modelos.Arrendador;
import com.arrienda.proyecto.repositorios.RepositorioArrendador;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ServicioArrendadorTest {

    @Autowired
    private ServicioArrendador servicioArrendador;

    @MockBean
    private RepositorioArrendador repositorioArrendador;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testTraerArrendadores() {
        // Crea algunos arrendadores de prueba
        Arrendador arrendador1 = new Arrendador();
        arrendador1.setId(1L);
        arrendador1.setNombre("Juan");
        arrendador1.setCorreo("juan@example.com");

        Arrendador arrendador2 = new Arrendador();
        arrendador2.setId(2L);
        arrendador2.setNombre("Maria");
        arrendador2.setCorreo("maria@example.com");

        // Crea DTOs correspondientes a los arrendadores
        DTOArrendador dtoArrendador1 = new DTOArrendador();
        dtoArrendador1.setId(1L);
        dtoArrendador1.setNombre("Juan");
        dtoArrendador1.setCorreo("juan@example.com");

        DTOArrendador dtoArrendador2 = new DTOArrendador();
        dtoArrendador2.setId(2L);
        dtoArrendador2.setNombre("Maria");
        dtoArrendador2.setCorreo("maria@example.com");

        // Configura el comportamiento del mock del repositorio y del modelMapper
        when(repositorioArrendador.findAll()).thenReturn(Arrays.asList(arrendador1, arrendador2));
        when(modelMapper.map(arrendador1, DTOArrendador.class)).thenReturn(dtoArrendador1);
        when(modelMapper.map(arrendador2, DTOArrendador.class)).thenReturn(dtoArrendador2);

        // Llama al método a probar
        List<DTOArrendador> resultado = servicioArrendador.traerArrendadores();

        // Verifica el resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Maria", resultado.get(1).getNombre());

        // Verifica que el repositorio y el modelMapper se llamaron correctamente
        verify(repositorioArrendador, times(1)).findAll();
        verify(modelMapper, times(1)).map(arrendador1, DTOArrendador.class);
        verify(modelMapper, times(1)).map(arrendador2, DTOArrendador.class);
    }

    @Test
    public void testTraerArrendador() {
        Long id = 1L;
        Arrendador arrendador = new Arrendador();
        arrendador.setId(id);

        when(repositorioArrendador.findById(id)).thenReturn(Optional.of(arrendador));
        when(modelMapper.map(any(Arrendador.class), eq(DTOArrendador.class)))
                .thenReturn(new DTOArrendador());

        DTOArrendador result = servicioArrendador.traerArrendador(id);

        assertNotNull(result);
        verify(repositorioArrendador, times(1)).findById(id);
    }

    @Test
    public void testTraerArrendadorNotFound() {
        Long id = 1L;

        when(repositorioArrendador.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> servicioArrendador.traerArrendador(id));

        verify(repositorioArrendador, times(1)).findById(id);
    }

    @Test
    public void testCrearArrendador() {
        DTOArrendador dtoArrendador = new DTOArrendador();
        dtoArrendador.setUsuario("juan");

        Arrendador arrendador = new Arrendador();

        when(repositorioArrendador.existsByUsuario(dtoArrendador.getUsuario())).thenReturn(false);
        when(modelMapper.map(dtoArrendador, Arrendador.class)).thenReturn(arrendador);
        when(repositorioArrendador.save(arrendador)).thenReturn(arrendador);
        when(modelMapper.map(arrendador, DTOArrendador.class)).thenReturn(dtoArrendador);

        DTOArrendador result = servicioArrendador.crearArrendador(dtoArrendador);

        assertNotNull(result);
        verify(repositorioArrendador, times(1)).existsByUsuario(dtoArrendador.getUsuario());
        verify(repositorioArrendador, times(1)).save(arrendador);
    }

    @Test
    public void testCrearArrendadorUsuarioExistente() {
        // Crea un DTOArrendador con un usuario que ya existe
        DTOArrendador dtoArrendador = new DTOArrendador();
        dtoArrendador.setUsuario("usuarioExistente");

        // Configura el comportamiento del mock del repositorio para simular que el
        // usuario ya existe
        when(repositorioArrendador.existsByUsuario(dtoArrendador.getUsuario())).thenReturn(true);

        // Verifica que se lanza una excepción IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            servicioArrendador.crearArrendador(dtoArrendador);
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertEquals("El arrendador con este usuario ya existe.", thrown.getMessage());

        // Verifica que el repositorio no guarda el arrendador
        verify(repositorioArrendador, times(1)).existsByUsuario(dtoArrendador.getUsuario());
        verify(repositorioArrendador, never()).save(any(Arrendador.class));
    }

    @Test
    public void testActualizarArrendador() {
        Long id = 1L;
        DTOArrendador dtoArrendador = new DTOArrendador();
        dtoArrendador.setNombre("Juan Actualizado");

        Arrendador arrendador = new Arrendador();

        when(repositorioArrendador.findById(id)).thenReturn(Optional.of(arrendador));
        when(repositorioArrendador.save(arrendador)).thenReturn(arrendador);
        when(modelMapper.map(dtoArrendador, Arrendador.class)).thenReturn(arrendador);
        when(modelMapper.map(arrendador, DTOArrendador.class)).thenReturn(dtoArrendador);

        DTOArrendador result = servicioArrendador.actualizarArrendador(id, dtoArrendador);

        assertNotNull(result);
        verify(repositorioArrendador, times(1)).findById(id);
        verify(repositorioArrendador, times(1)).save(arrendador);
    }

    @Test
    public void testEliminarArrendador() {
        Long id = 1L;
        Arrendador arrendador = new Arrendador();

        when(repositorioArrendador.findById(id)).thenReturn(Optional.of(arrendador));

        servicioArrendador.eliminarArrendador(id);

        verify(repositorioArrendador, times(1)).findById(id);
        verify(repositorioArrendador, times(1)).delete(arrendador);
    }

    /*
     * @Test
     * public void testEliminarArrendadorNotFound() {
     * Long id = 1L;
     * 
     * when(repositorioArrendador.findById(id)).thenReturn(Optional.empty());
     * 
     * assertThrows(EntityNotFoundException.class, () ->
     * servicioArrendador.eliminarArrendador(id));
     * 
     * verify(repositorioArrendador, times(1)).findById(id);
     * }
     */
}
