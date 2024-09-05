package com.arrienda.proyecto.controladores;

import com.arrienda.proyecto.dtos.AuthRequest;
import com.arrienda.proyecto.modelos.Usuario;
import com.arrienda.proyecto.servicios.ServicioUsuarioDetalle;
import com.arrienda.proyecto.utilidades.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // Importación necesaria
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private ServicioUsuarioDetalle usuarioDetalleServicio;

    @MockBean
    private JWTUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthRequest authRequest;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequest();
        authRequest.setUsuario("testuser");
        authRequest.setContrasena("testpassword");

        // Configurar manualmente el contexto de seguridad
        Usuario mockUsuario = new Usuario("testuser", "testpassword", "Nombre", "test@example.com",
                Usuario.Rol.ARRENDADOR, 0);
        mockUsuario.setId(1L);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                mockUsuario, null, mockUsuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testLoginExitoso() throws Exception {
        // Simular un Usuario que implementa UserDetails
        Usuario mockUsuario = new Usuario("testuser", "testpassword", "Nombre", "test@example.com",
                Usuario.Rol.ARRENDADOR, 0);
        mockUsuario.setId(1L);

        System.out.println("Mock usuario: " + mockUsuario);

        // Simular la respuesta de usuarioDetalleServicio y JWTUtil
        when(usuarioDetalleServicio.loadUserByUsername("testuser")).thenReturn(mockUsuario);

        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("mocked-jwt-token");

        when(jwtUtil.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);

        // Simular la autenticación exitosa
        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken("testuser", null, mockUsuario.getAuthorities()));

        // Realizar la petición POST
        mockMvc.perform(post("/auth/login")
                .with(csrf()) // Esto simula un CSRF token válido
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }

    @Test
    public void testLoginFallido() throws Exception {
        // Simular fallo en la autenticación con una excepción específica
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Authentication failed"));

        // Verifica que authRequest tenga credenciales incorrectas para provocar el
        // fallo
        authRequest.setUsuario("wronguser");
        authRequest.setContrasena("wrongpassword");

        // Realizar la petición POST con credenciales incorrectas
        mockMvc.perform(post("/auth/login")
                .with(csrf()) // Simula el CSRF token, si es necesario
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest))) // Enviar el objeto AuthRequest en formato JSON
                .andExpect(status().isUnauthorized()); // Verificar que el estado sea 401 Unauthorized
    }

}
