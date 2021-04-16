package com.springbootsecurityjwt.resource;


import com.springbootsecurityjwt.model.AuthenticationRequest;
import com.springbootsecurityjwt.model.AuthenticationResponse;
import com.springbootsecurityjwt.service.MyUserDetailService;

import com.springbootsecurityjwt.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JWTUtil jwtUtil;


    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> createAuthToken(@RequestBody AuthenticationRequest request) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        }catch(BadCredentialsException e){
            throw new Exception("Username or password doesn't exist");
        }
        final var details = myUserDetailService.loadUserByUsername(request.username());
        final var jwtToken = jwtUtil.generateToken(details);

        var authenticationResponse = new AuthenticationResponse(jwtToken);
        return new ResponseEntity<AuthenticationResponse>(authenticationResponse, HttpStatus.CREATED);
    }
}
