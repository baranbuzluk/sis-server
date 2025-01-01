package com.sis.server.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserType userType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status = UserStatus.ACTIVE;

  @Setter(AccessLevel.PRIVATE)
  private int incorrectLoginAttempts;

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<LoginAttempt> loginAttempts;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return userType == null ? Collections.emptySet() : Set.of(userType);
  }

  @Override
  public boolean isEnabled() {
    return status == UserStatus.ACTIVE;
  }

  @Override
  public boolean isAccountNonLocked() {
    return status != UserStatus.LOCKED;
  }

  public void incrementIncorrectLoginAttempts() {
    if (isEnabled()) incorrectLoginAttempts++;
  }

  public void clearIncorrectLoginAttempts() {
    incorrectLoginAttempts = 0;
  }
}
