package com.alin.task_management_project.security;

import com.alin.task_management_project.services.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService ;
    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService
    ){
        this.jwtService = jwtService;
        this.customUserDetailsService  = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = this.getTokenFromHeader(authHeader);


        if(token != null){
            try{
                String name = jwtService.extractName(token);
                String role = jwtService.extractRole(token);
               // UserDetails userDetails = customUserDetailsService.loadUserByUsername(name);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        name,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            catch (JwtException ex){
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                response.getWriter().write("""
            {
                "status": 401,
                "message": "Invalid or expired token"
            }
            """);
                return;
            }
        }

        filterChain.doFilter(request,response);
    }

    public String getTokenFromHeader(String header){
        if(header == null || !header.startsWith("Bearer ")){
            return null;
        }
        int index = header.indexOf("Bearer ");
        return header.substring(index + 7);
    }
}
