package com.freenow.security.jwt;

import com.freenow.security.enums.AuthorityTypes;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Component
public class JwtTokenProvider
{

    @Autowired private JwtProperties jwtProperties;

    @Autowired private UserDetailsService userDetailsService;

    private String secretKey;


    @PostConstruct
    protected void init()
    {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }


    public String createToken(String username, Set<AuthorityTypes> roles)
    {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles.stream().map(Enum::toString));

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }


    Authentication getAuthentication(String token)
    {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(
            userDetails, "username: " + userDetails.getUsername() + " password: " + userDetails.getPassword(), userDetails.getAuthorities());
    }


    private String getUsername(String token)
    {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    String resolveToken(HttpServletRequest req)
    {
        String bearerToken = req.getHeader("Authorization");
        return bearerToken != null && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : null;
    }


    boolean validateToken(String token)
    {
        try
        {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        }
        catch (JwtException | IllegalArgumentException e)
        {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

}
