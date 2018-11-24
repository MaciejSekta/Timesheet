package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.JwtTokenDTO;
import com.msekta.timesheet.DTOs.user.LoginDTO;
import com.msekta.timesheet.services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.msekta.timesheet.security.JwtConstants.TOKEN_TYPE;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<JwtTokenDTO> signin(@RequestBody LoginDTO user) {
        System.out.println("I am here");
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final JwtTokenDTO jwtToken = JwtTokenDTO.builder()
                                                .token(tokenService.createToken(authentication))
                                                .type(TOKEN_TYPE)
                                                .build();
        return ResponseEntity.ok(jwtToken);
    }
}