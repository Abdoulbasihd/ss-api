package com.spacestudent.ssapi.security;

import com.spacestudent.ssapi.model.User;
import com.spacestudent.ssapi.service.auth.UserService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret.key}")
    private String securityJwtSecretKey;

    @Value("${security.jwt.expire.length}")
    private int securityJwtExpireLength;

    @Autowired
    private UserService userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);


    @PostConstruct
    protected void init() {
        securityJwtSecretKey = Base64.getEncoder().encodeToString(securityJwtSecretKey.getBytes());
    }

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + securityJwtExpireLength);
        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, securityJwtSecretKey)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        User userDetails = (User) this.userDetailsService.loadUserByUsername(getUsername(token));
        LOGGER.info("gotten user in provider : {}", userDetails.getUsername());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(securityJwtSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            //return bearerToken.substring(7, bearerToken.length());
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        /*try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(securityJwtSecretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }*/
        Jws<Claims> claims = Jwts.parser().setSigningKey(securityJwtSecretKey).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

}
