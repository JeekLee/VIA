package com.via.support.security.config;

import com.via.support.security.annotation.CurrentUserIdArgumentResolver;
import com.via.support.security.handler.AuthenticationFailureHandler;
import com.via.support.security.handler.AuthorizationFailureEntryPoint;
import com.via.support.security.jwt.JwtResolver;
import com.via.support.security.jwt.filter.JwtAuthenticationFilter;
import com.via.support.security.jwt.filter.SecurityExceptionHandlerFilter;
import com.via.support.security.properties.CorsProperties;
import com.via.support.security.properties.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig implements WebMvcConfigurer {
    private final CorsProperties corsProperties;
    private final JwtProperties jwtProperties;
    private final JwtResolver jwtResolver;
    private final ObjectMapper objectMapper;
    private final CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthorizationFailureEntryPoint();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint())
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtProperties, jwtResolver), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SecurityExceptionHandlerFilter(objectMapper, jwtProperties), JwtAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(corsProperties.getAllowedOrigins()));
        configuration.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods()));
        configuration.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders()));
        configuration.setExposedHeaders(Arrays.asList(corsProperties.getExposedHeaders()));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserIdArgumentResolver);
    }
}
