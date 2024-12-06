package com.sis.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class LoginRequest {
  private String username;
  private String password;
}
