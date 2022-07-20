package com.example.supportportal.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.supportportal.Constants.SecurityConstants;
import com.example.supportportal.domain.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class JWTTokenProvider {

    @Value("${jwt.secrect}")
    private String secrect;

    public String generateJWTToken(UserPrincipal userPrincipal){
    String[]claims=getClaimsFromUser(userPrincipal);
    return JWT.create()
            .withIssuer(SecurityConstants.GET_ARRAYS_LLC)
            .withAudience(SecurityConstants.GET_ARRAYS_ADMINSTRATION)
            .withIssuedAt(new Date())
            .withSubject(userPrincipal.getUsername())
            .withArrayClaim(SecurityConstants.AUTHORITIES,claims)
            .withExpiresAt(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(secrect.getBytes()));
    }

    public List<GrantedAuthority>getAuthorities(String token){
        String []claims=getClaimsForToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private String[] getClaimsForToken(String token) {
        JWTVerifier verifier=getJWTVerifier();

        return verifier.verify(token).getClaim(SecurityConstants.AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try{
            Algorithm algorithm=Algorithm.HMAC512(secrect);
            verifier=JWT.require(algorithm).withIssuer(SecurityConstants.GET_ARRAYS_LLC).build();
        }
        catch(JWTVerificationException jwtVerificationException){
           throw  new JWTVerificationException(SecurityConstants.TOKEN_CANNOT_BE_VERIFIED);
        }
            return verifier;
    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        List<String> authorities=new ArrayList<>();
        for (GrantedAuthority grantedAuthority:userPrincipal.getAuthorities()
             ) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

}
