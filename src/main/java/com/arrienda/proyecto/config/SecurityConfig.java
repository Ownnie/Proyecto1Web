package com.arrienda.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.arrienda.proyecto.servicios.ServicioUsuarioDetalle;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ServicioUsuarioDetalle usuarioDetalleServicio;
    private final JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(ServicioUsuarioDetalle usuarioDetalleServicio, JWTRequestFilter jwtRequestFilter) {
        this.usuarioDetalleServicio = usuarioDetalleServicio;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(usuarioDetalleServicio)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/registro").permitAll()
                        .requestMatchers("/arrendador/**").hasRole("ARRENDADOR")
                        .requestMatchers("/arrendatario/**").hasRole("ARRENDATARIO")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Añadir el filtro JWT antes del filtro de autenticación de usuario y
        // contraseña
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
