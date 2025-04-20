package com.shahrozz.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 👈 updated csrf disabling
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/accounts/**").authenticated() // 👈 updated authorize
                        .anyRequest().permitAll()
                )
                .httpBasic(basic -> {}); // 👈 updated httpBasic, no extra config for now

        return http.build();
    }
}
