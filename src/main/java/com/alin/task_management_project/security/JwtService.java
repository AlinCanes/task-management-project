package com.alin.task_management_project.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private  String secretKey;
    @Value("${jwt.expiration}")
    private  int jwtExpiration;

    private SecretKey key;

 @PostConstruct
 public void init(){
     this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
 }

    public String generateToken(String name,String role){
        return Jwts.builder()
                .subject(name)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    public String extractName(String token){
     return Jwts.parser()
             .verifyWith(key)
             .build()
             .parseSignedClaims(token)
             .getPayload()
             .getSubject();
    }
    public  String extractRole(String token){
     return  Jwts.parser()
             .verifyWith(key)
             .build()
             .parseSignedClaims(token)
             .getPayload()
             .get("role",String.class);
    }
}
