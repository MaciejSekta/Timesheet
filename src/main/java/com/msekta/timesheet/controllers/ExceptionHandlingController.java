package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> badCredentioalnHandler(BadCredentialsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(ErrorResponseDTO.builder()
                                                   .exception(ex)
                                                   .path(request.getRequestURI())
                                                   .httpStatus(HttpStatus.UNAUTHORIZED)
                                                   .build());
    }
}
