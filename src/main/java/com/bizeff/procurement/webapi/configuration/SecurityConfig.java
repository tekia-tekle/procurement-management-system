package com.bizeff.procurement.webapi.configuration;

import com.bizeff.procurement.webapi.utility.JwtAuthenticationFilter;
import com.bizeff.procurement.webapi.utility.Role;
import com.bizeff.procurement.webapi.utility.RoleUrlMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {

                    // Public endpoints (no auth required)
                    auth.requestMatchers("/api/auth/**",
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/webjars/**").permitAll();

                    // Role-based access for all other endpoints
                    RoleUrlMapping.getUrlToRoleMapping().forEach((url, roles) -> {
                        for (Role role : roles) {
                            auth.requestMatchers(url).hasRole(role.name());
                        }
                    });

                    // Everything else requires authentication
                    auth.anyRequest().authenticated();
                });

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
