package com.specific.group.currency.client.util;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

public class TokenUtil {

    public static String getEmailFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        String jwtWithoutSignature = jwt.substring(0, jwt.lastIndexOf('.') + 1);
        String email;
        try {
            email = Jwts.parserBuilder()
                    .build()
                    .parseClaimsJwt(jwtWithoutSignature)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("Token has expired!");
        }
        return email;
    }
}
