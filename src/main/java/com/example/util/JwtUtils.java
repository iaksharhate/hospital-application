package com.example.util;

import com.example.model.User;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "YourSecretKey";

    public String generateToken(User user) {

        // Create claims and include user data
        return Jwts.builder()
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getUser())
                .setHeaderParam(JwsHeader.ALGORITHM, "HS256")
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
