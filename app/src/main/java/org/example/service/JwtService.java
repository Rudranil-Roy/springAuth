package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public static final String secret = "y98DK3sHoO5Ww4LqOLR9QeNksW16nYu+cnz0D5gZB6A2pv6AY6KQz4+aWiGbG7BrZRoXnPYc7uMJ5sFTH6R70w==\n";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
    }
}
