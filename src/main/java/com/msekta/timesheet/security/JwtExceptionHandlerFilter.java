package com.msekta.timesheet.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msekta.timesheet.DTOs.ErrorResponseDTO;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                                                             .httpStatus(HttpStatus.UNAUTHORIZED)
                                                             .path(request.getRequestURI())
                                                             .exception(e)
                                                             .build();

            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(errorResponse.getStatus());
            response.getWriter().write(mapper.writeValueAsString(errorResponse));
        }
    }
}
