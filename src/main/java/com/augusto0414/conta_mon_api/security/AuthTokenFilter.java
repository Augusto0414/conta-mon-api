package com.augusto0414.conta_mon_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Este metodo va a realizar todos los filtros relacionados al token
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lo primero que debemos hacer es obtener el token del request
        final String token = getTokenRequest(request);
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);

    }

    private String getTokenRequest(HttpServletRequest request) {
         final String getHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
         if(!StringUtils.hasText(getHeader) || !getHeader.startsWith("Bearer ")) return null;
         final String [] token = getHeader.trim().split("Bearer");
         return token.length > 1 ? token[1] : "";
    }

    // filter chain es la cadena de filtros que configuramos previamente

}
