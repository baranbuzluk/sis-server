package com.sis.server.entity;

import org.springframework.security.core.GrantedAuthority;

public enum AuthorityType implements GrantedAuthority {
  STUDENT,
  TEACHER,
  ADMIN;

  @Override
  public String getAuthority() {
    return "ROLE_" + name();
  }
}
