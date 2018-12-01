package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.JwtTokenDTO;
import com.msekta.timesheet.DTOs.user.LoginDTO;
import com.msekta.timesheet.services.ActivationService;
import com.msekta.timesheet.services.JwtTokenService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.msekta.timesheet.security.JwtConstants.TOKEN_TYPE;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenService;
    private final ActivationService activationService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenService tokenService,
			ActivationService activationService) {
		super();
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
		this.activationService = activationService;
	}

    @CrossOrigin(origins = "http://localhost:4200")
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
    
	@PutMapping(path = "/password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") Long id){
    	try {
    		activationService.changePassword(id);
    		return ResponseEntity.ok().build();
    	}catch (Exception e) {
    		log.info(e.getMessage(), e);
			return ResponseEntity.badRequest().build();
		}
    }
}