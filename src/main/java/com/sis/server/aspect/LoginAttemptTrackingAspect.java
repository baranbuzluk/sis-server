package com.sis.server.aspect;

import com.sis.server.common.ObjectUtils;
import com.sis.server.dto.LoginRequest;
import com.sis.server.entity.LoginAttempt;
import com.sis.server.entity.User;
import com.sis.server.entity.UserStatus;
import com.sis.server.enums.LoginAttemptStatus;
import com.sis.server.repository.LoginAttemptRepository;
import com.sis.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginAttemptTrackingAspect {
  private final UserRepository userRepository;
  private final LoginAttemptRepository loginAttemptRepository;

  @Value("${login.attempts.failed}")
  private Integer loginAttemptsFailed;

  @Around("@annotation(LoginAttemptTracking)")
  public Object handleLoginAttempt(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      Object proceeded = joinPoint.proceed();
      commitLoginAttempt(joinPoint, LoginAttemptStatus.SUCCESS);
      return proceeded;
    } catch (BadCredentialsException exception) {
      commitLoginAttempt(joinPoint, LoginAttemptStatus.FAILURE);
      throw exception;
    } catch (LockedException exception) {
      commitLoginAttempt(joinPoint, LoginAttemptStatus.LOCKED);
      throw exception;
    } catch (DisabledException exception) {
      commitLoginAttempt(joinPoint, LoginAttemptStatus.BLOCKED);
      throw exception;
    }
  }

  private void commitLoginAttempt(ProceedingJoinPoint joinPoint, LoginAttemptStatus status) {
    LoginRequest login = ObjectUtils.findInstanceOf(LoginRequest.class, joinPoint.getArgs());
    Assert.notNull(login, "instance of LoginRequest should be in the parameters");
    final User user = userRepository.findByUsername(login.getUsername());
    if (user == null) {
      log.warn("User not found for username: {}", login.getUsername());
      return;
    }
    saveLoginAttempt(status, user);
    updateUser(status, user);
  }

  private void updateUser(LoginAttemptStatus status, User user) {
    final int loginAttemptsBefore = user.getIncorrectLoginAttempts();
    final UserStatus statusBefore = user.getStatus();
    if (status == LoginAttemptStatus.SUCCESS) user.clearIncorrectLoginAttempts();
    if (status == LoginAttemptStatus.FAILURE) user.incrementIncorrectLoginAttempts();
    if (user.getIncorrectLoginAttempts() >= loginAttemptsFailed) user.setStatus(UserStatus.LOCKED);

    if (loginAttemptsBefore != user.getIncorrectLoginAttempts() || statusBefore != user.getStatus())
      userRepository.save(user);
  }

  private void saveLoginAttempt(LoginAttemptStatus status, User user) {
    LoginAttempt attempt = new LoginAttempt();
    attempt.setUser(user);
    attempt.setStatus(status);
    loginAttemptRepository.save(attempt);
  }
}
