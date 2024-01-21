package com.openclassrooms.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openclassrooms.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("Request received: " + request.getMethod() + " " + request.getRequestURI());

        Authentication authentication = JwtUtils.getAuthentication(JwtUtils.extractToken(request));
        if (request.getHeader("authorization") == null) {
            System.out.println("Authorization header not present. Allowing request to pass through.");
            filterChain.doFilter(request, response);
            return;
        } else if (JwtUtils.isTokenValid(authentication)) {
            System.out.println("Token is valid. Setting authentication in SecurityContextHolder.");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } else {
            System.out.println("Unauthorized access. Sending 401 UNAUTHORIZED response.");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized access");
        }
    }
}
