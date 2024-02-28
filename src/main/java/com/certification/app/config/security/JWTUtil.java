package com.certification.app.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    @Value("${jjwt.secret}")
    private String secret;
    @Value("${jjwt.expiration}")
    private String expirationTime;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        return getSecret(claims, user.getUsername());
    }

    public String getSecret(Map<String, Object> claims, String userName) {
        long expirationTimeInSeconds = Long.parseLong(expirationTime);

        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + expirationTimeInSeconds * 1000); // Gets the expiration date based on minutes

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes()); //Decodify keys

        return Jwts.builder()
                .addClaims(claims) // Adds payload
                .setSubject(userName) // Adds the creator
                .setIssuedAt(creationDate) // Creation date
                .setExpiration(expirationDate) // Expiration date
                .signWith(key) // secret
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(token.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserNameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        final Date expirationDate = getAllClaimsFromToken(token).getExpiration();

        return expirationDate.before(new Date());
    }

}
