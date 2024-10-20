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

import com.arrienda.proyecto.dtos.DTOUsuario;
import com.arrienda.proyecto.modelos.Usuario;
import com.arrienda.proyecto.repositorios.RepositorioUsuario;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ServicioUsuarioTest {
    @Autowired
    private ServicioUsuario servicioUsuario;

    @MockBean
    private RepositorioUsuario repositorioUsuario;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testTraerUsuarioes() {
        // Crea algunos Usuarioes de prueba
        Usuario Usuario1 = new Usuario();
        Usuario1.setId(1L);
        Usuario1.setNombre("Juan");
        Usuario1.setCorreo("juan@example.com");

        Usuario Usuario2 = new Usuario();
        Usuario2.setId(2L);
        Usuario2.setNombre("Maria");
        Usuario2.setCorreo("maria@example.com");

        // Crea DTOs correspondientes a los Usuarioes
        DTOUsuario dtoUsuario1 = new DTOUsuario();
        dtoUsuario1.setId(1L);
        dtoUsuario1.setNombre("Juan");
        dtoUsuario1.setCorreo("juan@example.com");

        DTOUsuario dtoUsuario2 = new DTOUsuario();
        dtoUsuario2.setId(2L);
        dtoUsuario2.setNombre("Maria");
        dtoUsuario2.setCorreo("maria@example.com");

        // Configura el comportamiento del mock del repositorio y del modelMapper
        when(repositorioUsuario.findAll()).thenReturn(Arrays.asList(Usuario1, Usuario2));
        when(modelMapper.map(Usuario1, DTOUsuario.class)).thenReturn(dtoUsuario1);
        when(modelMapper.map(Usuario2, DTOUsuario.class)).thenReturn(dtoUsuario2);

        // Llama al método a probar
        List<DTOUsuario> resultado = servicioUsuario.traerUsuarios();

        // Verifica el resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Maria", resultado.get(1).getNombre());

        // Verifica que el repositorio y el modelMapper se llamaron correctamente
        verify(repositorioUsuario, times(1)).findAll();
        verify(modelMapper, times(1)).map(Usuario1, DTOUsuario.class);
        verify(modelMapper, times(1)).map(Usuario2, DTOUsuario.class);
    }

    @Test
    public void testTraerUsuario() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(repositorioUsuario.findById(id)).thenReturn(Optional.of(usuario));
        when(modelMapper.map(any(Usuario.class), eq(DTOUsuario.class)))
                .thenReturn(new DTOUsuario());

        DTOUsuario result = servicioUsuario.traerUsuario(id);

        assertNotNull(result);
        verify(repositorioUsuario, times(1)).findById(id);
    }

    @Test
    public void testTraerUsuarioNotFound() {
        Long id = 1L;

        when(repositorioUsuario.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> servicioUsuario.traerUsuario(id));

        verify(repositorioUsuario, times(1)).findById(id);
    }

    @Test
    public void testCrearUsuario() {
        DTOUsuario dtoUsuario = new DTOUsuario();
        dtoUsuario.setUsuario("juan");

        Usuario usuario = new Usuario();

        when(repositorioUsuario.existsByUsuario(dtoUsuario.getUsuario())).thenReturn(false);
        when(modelMapper.map(dtoUsuario, Usuario.class)).thenReturn(usuario);
        when(repositorioUsuario.save(usuario)).thenReturn(usuario);
        when(modelMapper.map(usuario, DTOUsuario.class)).thenReturn(dtoUsuario);

        DTOUsuario result = servicioUsuario.crearUsuario(dtoUsuario);

        assertNotNull(result);
        verify(repositorioUsuario, times(1)).existsByUsuario(dtoUsuario.getUsuario());
        verify(repositorioUsuario, times(1)).save(usuario);
    }

    @Test
    public void testCrearUsuarioUsuarioExistente() {
        // Crea un DTOUsuario con un usuario que ya existe
        DTOUsuario dtoUsuario = new DTOUsuario();
        dtoUsuario.setUsuario("usuarioExistente");

        // Configura el comportamiento del mock del repositorio para simular que el
        // usuario ya existe
        when(repositorioUsuario.existsByUsuario(dtoUsuario.getUsuario())).thenReturn(true);

        // Verifica que se lanza una excepción IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            servicioUsuario.crearUsuario(dtoUsuario);
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertEquals("El Usuario con este usuario ya existe.", thrown.getMessage());

        // Verifica que el repositorio no guarda el Usuario
        verify(repositorioUsuario, times(1)).existsByUsuario(dtoUsuario.getUsuario());
        verify(repositorioUsuario, never()).save(any(Usuario.class));
    }

    @Test
    public void testActualizarUsuario() {
        Long id = 1L;
        DTOUsuario dtoUsuario = new DTOUsuario();
        dtoUsuario.setNombre("Juan Actualizado");

        Usuario usuario = new Usuario();

        when(repositorioUsuario.findById(id)).thenReturn(Optional.of(usuario));
        when(repositorioUsuario.save(usuario)).thenReturn(usuario);
        when(modelMapper.map(dtoUsuario, Usuario.class)).thenReturn(usuario);
        when(modelMapper.map(usuario, DTOUsuario.class)).thenReturn(dtoUsuario);

        DTOUsuario result = servicioUsuario.actualizarUsuario(id, dtoUsuario);

        assertNotNull(result);
        verify(repositorioUsuario, times(1)).findById(id);
        verify(repositorioUsuario, times(1)).save(usuario);
    }

    @Test
    public void testEliminarUsuario() {
        Long id = 1L;
        Usuario usuario = new Usuario();

        when(repositorioUsuario.findById(id)).thenReturn(Optional.of(usuario));

        servicioUsuario.eliminarUsuario(id);

        verify(repositorioUsuario, times(1)).findById(id);
        verify(repositorioUsuario, times(1)).delete(usuario);
    }
}
