package net.ricebean.zplbox.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.ricebean.zplbox.config.RapidApiSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
@Profile("rapidapi")
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RapidApiSecurityConfig.class);

    private static final String API_SECRET_HEADER = "X-RapidAPI-Proxy-Secret";

    @Value("${rapidapi.secret}")
    private String rapidapiSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestSecret = request.getHeader(API_SECRET_HEADER);

        if (rapidapiSecret.equals(requestSecret)) {
            var authentication = new UsernamePasswordAuthenticationToken(
                    "api-user", // principal
                    null,       // credentials
                    AuthorityUtils.createAuthorityList("ROLE_USER") // authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}