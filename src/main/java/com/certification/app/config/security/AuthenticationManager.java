package com.certification.app.config.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        String username = jwtUtil.getUserNameFromToken(token);

        if (Objects.nonNull(username) && !jwtUtil.isTokenExpired(token)) {
            Claims claims = jwtUtil.getAllClaimsFromToken(token);

            List<String> roles = claims.get("roles", List.class);

            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, roles.stream().map(SimpleGrantedAuthority::new).toList()));

        } else {
            return Mono.error(new InterruptedException("Token not valid or expired."));
        }

    }

}
