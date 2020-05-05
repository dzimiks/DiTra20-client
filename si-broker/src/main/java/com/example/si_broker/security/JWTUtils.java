package com.example.si_broker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.si_broker.utils.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    public String generateJWTToken(Authentication authentication) {
        return JWT.create()
                .withSubject(((User) authentication.getPrincipal()).getUsername())
                .withClaim("test1", "test2")
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(Constants.SECRET_KEY.getBytes()));
    }

    public String getUsernameFromJWTToken(String token) {
        return JWT.require(
                Algorithm.HMAC512(Constants.SECRET_KEY.getBytes()))
                .build()
                .verify(token.replace(Constants.TOKEN_PREFIX, ""))
                .getSubject();
    }

    public boolean validateJWTToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }

        return false;
    }
}
