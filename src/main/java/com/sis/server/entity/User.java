package com.sis.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "users")
@Setter
@Getter
public class User implements UserDetails {
  @Id private Integer id;
  private String username;
  private String password;
  private String email;
  private Integer studentNumber;

  @Enumerated(EnumType.STRING)
  private AuthorityType authorityType;

  /**
   * @param rawPassword text to hash as SHA-256
   */
  public void setPassword(String rawPassword) {
    final byte[] bytes = DigestUtils.sha256(rawPassword);
    this.password = new String(bytes, StandardCharsets.UTF_8);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorityType == null ? Collections.emptySet() : Set.of(authorityType);
  }
}
