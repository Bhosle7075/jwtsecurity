package com.praveen.security.jwtsecurity.contoller;

import com.praveen.security.jwtsecurity.dto.LoginRequest;
import com.praveen.security.jwtsecurity.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Authentication authenticated = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(request.userName(), request.password()));
        String token = jwtUtils.generateToken(request.userName());
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
