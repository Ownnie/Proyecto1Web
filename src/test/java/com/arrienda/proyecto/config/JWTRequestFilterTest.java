package com.arrienda.proyecto.config;

import com.arrienda.proyecto.servicios.ServicioUsuarioDetalle;
import com.arrienda.proyecto.utilidades.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class JWTRequestFilterTest {

    @Mock
    private ServicioUsuarioDetalle servicioUsuarioDetalle;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private JWTRequestFilter jwtRequestFilter;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails mockUserDetails;

    private String username;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilterInternal_WithValidJWTToken_ShouldAuthenticate() throws Exception {
        // Simular token y nombre de usuario
        String token = "Bearer valid-jwt-token";
        String username = "testuser";

        // Mock del request para devolver el header de autorización con el token
        when(httpServletRequest.getHeader("Authorization")).thenReturn(token);

        // Mock del JWTUtil para devolver el nombre de usuario válido y marcar el token
        // como válido
        when(jwtUtil.extractUsername(anyString())).thenReturn(username);
        when(jwtUtil.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);

        // Mock del servicio para devolver un UserDetails válido
        when(mockUserDetails.getUsername()).thenReturn(username);
        when(servicioUsuarioDetalle.loadUserByUsername(username)).thenReturn(mockUserDetails);

        // Ejecutar el filtro
        jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        // Verificar que el método loadUserByUsername fue llamado correctamente
        verify(servicioUsuarioDetalle).loadUserByUsername(username);

        // Verificar que la cadena de filtros continuó su ejecución
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);

        // Verificar que el contexto de seguridad fue poblado con la autenticación
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null; // Verificar que el objeto de autenticación no sea nulo

        // Asegurarse de que el principal (el usuario autenticado) sea el esperado
        assert auth.getPrincipal().equals(mockUserDetails);

        // Verificar que el token se haya validado correctamente (jwtUtil.validateToken
        // fue llamado)
        verify(jwtUtil).validateToken(anyString(), any(UserDetails.class));
    }

    @Test
    public void testDoFilterInternal_WithInvalidJWTToken_ShouldNotAuthenticate() throws Exception {
        // Mock del JWTUtil para devolver un nombre de usuario válido pero un token
        // inválido
        when(jwtUtil.extractUsername(anyString())).thenReturn(username);
        when(jwtUtil.validateToken(anyString(), any(UserDetails.class))).thenReturn(false);

        // Ejecutar el filtro
        jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        // Verificar que la autenticación NO fue configurada
        verify(servicioUsuarioDetalle, never()).loadUserByUsername(username);
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);

        // Asegurarse de que el contexto de seguridad permanece nulo
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

    @Test
    public void testDoFilterInternal_WithoutJWTToken_ShouldProceedWithoutAuthentication() throws Exception {
        // Simular que no hay header de autorización en el request
        when(httpServletRequest.getHeader("Authorization")).thenReturn(null);

        // Ejecutar el filtro
        jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        // Verificar que no hubo interacción con JWTUtil ni con ServicioUsuarioDetalle
        verify(jwtUtil, never()).extractUsername(anyString());
        verify(servicioUsuarioDetalle, never()).loadUserByUsername(anyString());
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);

        // Asegurarse de que el contexto de seguridad permanece nulo
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
