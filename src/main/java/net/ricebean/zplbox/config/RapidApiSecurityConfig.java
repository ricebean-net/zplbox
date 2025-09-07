package net.ricebean.zplbox.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("rapidapi")
public class RapidApiSecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(RapidApiSecurityConfig.class);

    private static final String RAPID_API_HEADER = "X-RapidAPI-Proxy-Secret";

    @Value("${rapidapi.secret}")
    private String rapidapiSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Define the Filter logic inline
        Filter customHeaderFilter = (ServletRequest request, ServletResponse response, FilterChain filterChain) -> {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            String headerValue = httpRequest.getHeader(RAPID_API_HEADER);

            if (headerValue == null || !headerValue.equals(rapidapiSecret)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            filterChain.doFilter(request, response);
        };

        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(customHeaderFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}
