package com.arrienda.proyecto.seguridad.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.arrienda.proyecto.seguridad.CustomUserDetailService;

import io.jsonwebtoken.Claims;

import java.io.IOException;

@Component

public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTTokenService jwtTokenService;

    private final CustomUserDetailService customUserDetailsService;

    private String username = null;
    private Claims claims = null;

    JWTAuthFilter(JWTTokenService jwtTokenService, CustomUserDetailService customUserDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().matches("/api/auth/register")
                || request.getServletPath().matches("/api/auth/login")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                username = jwtTokenService.extraerNombreUsuario(token);
                claims = jwtTokenService.extraerTodosLosClaims(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                if (jwtTokenService.validarToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    new WebAuthenticationDetailsSource().buildDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        }
    }

    public String getCurrentUser() {
        return username;
    }

    public Long getAuthId() {
        return (Long) claims.get("id");
    }
}
