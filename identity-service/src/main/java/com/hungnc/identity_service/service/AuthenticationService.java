package com.hungnc.identity_service.service;

import com.hungnc.identity_service.dto.AuthenticationRequest;
import com.hungnc.identity_service.dto.AuthenticationResponse;
import com.hungnc.identity_service.dto.IntrospectRequest;
import com.hungnc.identity_service.dto.IntrospectResponse;
import com.hungnc.identity_service.exception.AppException;
import com.hungnc.identity_service.exception.ErrorCode;
import com.hungnc.identity_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.signer-key}")
    protected String SECRET_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = this.generateToken(request.getUsername());
        AuthenticationResponse response = new AuthenticationResponse();
        response.setAuthenticated(true);
        response.setToken(token);

        return response;
    }

    public IntrospectResponse introspect(IntrospectRequest request) {

        String token = request.getToken();
        JWSVerifier verifier = null;
        try {
            verifier = new MACVerifier(SECRET_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean verified = signedJWT.verify(verifier);
            if (!verified) {
                throw new AppException(ErrorCode.TOKEN_INVALID);
            }

            IntrospectResponse response = new IntrospectResponse();
            response.setValid(verified && expiration.after(new Date()));
            return response;
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }

    }


    private String generateToken(String username) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("customClaims", "customClaims")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }

}
