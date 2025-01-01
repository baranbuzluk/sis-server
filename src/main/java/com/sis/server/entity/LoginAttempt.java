package com.sis.server.entity;

import com.sis.server.enums.LoginAttemptStatus;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
public class LoginAttempt {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @CreationTimestamp private Instant createdAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LoginAttemptStatus status;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
