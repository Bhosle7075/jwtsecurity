package com.praveen.security.jwtsecurity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.secret}")
    private String secretKey;
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    //Step:1 Generate Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("praveenIt")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    //Step:2 Read Token
    public Claims getClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    //Step3 : Get UserName
    public String getUserName(String jwtToken) {
        return getClaims(jwtToken).getSubject();
    }

    //Step4 : Get Expiry Date
    public Date getExpiryDate(String jwtToken) {
        return getClaims(jwtToken).getExpiration();
    }

    //Step5 : Validate expiration date
    public boolean isTokenExpired(String jwtToken) {
        Date expiryDate = getExpiryDate(jwtToken);
        return expiryDate.before(new Date(System.currentTimeMillis()));
    }

    //Step6 : Validate Token
    public boolean isTokenValid(String jwtToken,String dbUser){
        String userName = getUserName(jwtToken);
        return (userName.equalsIgnoreCase(dbUser) && !isTokenExpired(jwtToken));
    }

}
