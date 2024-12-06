package com.sis.server.controller;

import com.sis.server.dto.LoginRequest;
import com.sis.server.dto.LoginResponse;
import com.sis.server.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenService jwtTokenService;

  @PostMapping(
      path = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public LoginResponse login(@RequestBody LoginRequest request) {
    Authentication authenticated =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authenticated);

    String token = jwtTokenService.generateToken(request.getUsername());

    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setToken(token);
    return loginResponse;
  }
}
