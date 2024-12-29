package com.sis.server.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "users")
@Setter
@Getter
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private Integer studentNumber;

  private String password;

  @Column(unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  private AuthorityType authorityType;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorityType == null ? Collections.emptySet() : Set.of(authorityType);
  }

  @Override
  public String getUsername() {
    if (authorityType == AuthorityType.STUDENT) {
      final Integer studentNo = getStudentNumber();
      return studentNo == null ? "" : getStudentNumber().toString();
    }
    return getEmail();
  }
}
