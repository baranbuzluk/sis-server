package com.sis.server.controller;

import com.sis.server.dto.LoginRequest;
import com.sis.server.security.JwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenService jwtTokenService;

  @PostMapping(path = "/login")
  @ResponseStatus(HttpStatus.CREATED)
  public String login(@Valid @RequestBody LoginRequest request) {
    var authentication =
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    authenticationManager.authenticate(authentication);
    return jwtTokenService.generateToken(request.getUsername());
  }
}
