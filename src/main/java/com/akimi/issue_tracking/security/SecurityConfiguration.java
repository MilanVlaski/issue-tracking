package com.akimi.issue_tracking.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/register", "/login").permitAll() // Allow access to these pages
                        .requestMatchers("/reportProblem", "/application/**", "/problems").hasRole("USER")
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", false)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain engineerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/engineer/**")
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/engineer/login", "/engineer/register").permitAll() // Allow access to these pages
                        .requestMatchers("/engineer/**").hasRole("ENGINEER")
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/engineer/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/engineer/problems")
                )
                .logout(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
