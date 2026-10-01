package com.Blog.Blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String secret = "jwtTokenKey";

    // Retrieve username from token
    public String getUserNameFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from token
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    // Retrieve claim from token
    public <T> T getClaimsFromToken(String token,
                                    Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve all claims
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    // Check whether token is expired
    private Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate token
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, userDetails.getUsername());
    }

    // Create token
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                        + JwtTokenHelper.JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUserNameFromToken(token);

        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }
}