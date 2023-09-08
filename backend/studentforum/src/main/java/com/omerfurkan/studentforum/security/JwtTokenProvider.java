package com.omerfurkan.studentforum.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Component
public class JwtTokenProvider {
    @Value("${student.forum.app.secret}")
    private String APP_SECRET;

    public String generateJwtToken(Authentication authentication) throws Exception {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(now.plus(60, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, APP_SECRET)
                .compact();
    }

    public Long getUserIdFromJwt(String token) throws Exception {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);

            return !isTokenExpired(token);
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isTokenExpired(String token) throws Exception {
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();

        return expiration.before(new Date());
    }
}
