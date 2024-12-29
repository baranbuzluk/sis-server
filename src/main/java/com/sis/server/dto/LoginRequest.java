package com.sis.server.dto;

import com.sis.server.constraint.PasswordRule;
import com.sis.server.constraint.UsernameRule;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class LoginRequest {
  @NotNull(message = "Username cannot be null")
  @Size(min = 1, message = "Username must not be empty")
  @UsernameRule
  private String username;

  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  @PasswordRule
  private String password;
}
