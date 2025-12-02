package com.example.sis_spinner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// [BARU] Import untuk CORS
import org.springframework.web.cors.CorsConfiguration; 
import org.springframework.web.cors.CorsConfigurationSource; 
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; 
import java.util.Arrays;
import java.util.List;
import static org.springframework.security.config.Customizer.withDefaults; // [BARU] Import untuk Customizer

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // [BARU] Bean untuk Konfigurasi CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Ganti "http://localhost:3000" dengan domain frontend Anda
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept")); 
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Terapkan konfigurasi ini ke semua endpoint
        source.registerCorsConfiguration("/**", configuration); 
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults()) // [BARU] Aktifkan CORS dengan konfigurasi dari bean di atas
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Endpoint Auth & Public
                .requestMatchers("/api/auth/login").permitAll()
                // [Perbaikan] Ubah dari '/api/admins/register' menjadi '/api/auth/register'
                .requestMatchers("/api/auth/register").permitAll() 
                
                // Endpoint Swagger UI (WAJIB DIBUKA)
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()

                // Endpoint lainnya butuh autentikasi
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}