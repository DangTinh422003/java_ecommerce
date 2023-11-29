package com.springcommerce.security;

import com.springcommerce.configuration.JwtAuthenticationFilter;
import com.springcommerce.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private LogoutHandler logoutHandler;

    private final String[] routes = new String[] {
            "/",
            "/auth/**",
            "/admin/**",
            "/api/user/**",
            "/api/category/**",
            "/api/product/**",
            "/api/order/**",
            "/api/cart/**",
            "/api/cart_detail/**",
            "/api/order_detail/**",
            "/api/admin/**",
            "/user/**",
            "/product/**",
            "/cart/**",
            "/cart",
            "/order",
            "/order/**",
            "/success",
            "/categories/**",
            "/cart-detail/**",
            "/search/**",
            "/product-detail/**",
            "/admin",
            "/orders/**",
            "/cart",
            "/product",
            "/search",
            "error/**",
            "/order/**",
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/api/v1/auth/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                            authorize
                                    .requestMatchers(routes)
                                    .permitAll()
                                    .requestMatchers(new String[]{
                                            "/api/v1/admin/**"
                                    }).hasRole(Role.ADMIN.name())
                                    .requestMatchers(
                                            "/admin/**"
                                    ).hasRole(Role.ADMIN.name())
                                    .anyRequest().authenticated();
                        }
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()
                        )
                );
        return http.build();
    }
}
