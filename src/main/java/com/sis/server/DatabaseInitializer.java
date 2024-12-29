package com.sis.server;

import com.sis.server.entity.AuthorityType;
import com.sis.server.entity.User;
import com.sis.server.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DatabaseInitializer implements ApplicationRunner {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(ApplicationArguments args) {
    userRepository.saveAllAndFlush(List.of(createStudent(), createTeacher(), createAdmin()));
    log.info("Database initialized.");
  }

  private User createStudent() {
    User user = new User();
    user.setStudentNumber(1);
    user.setAuthorityType(AuthorityType.STUDENT);
    user.setPassword(passwordEncoder.encode("Example1!2024"));
    return user;
  }

  private User createTeacher() {
    User user = new User();
    user.setAuthorityType(AuthorityType.TEACHER);
    user.setEmail("teacher@school.edu");
    user.setPassword(passwordEncoder.encode("Secure$Pass123"));
    return user;
  }

  private User createAdmin() {
    User user = new User();
    user.setAuthorityType(AuthorityType.ADMIN);
    user.setEmail("admin@school.edu");
    user.setPassword(passwordEncoder.encode("NewYear#2024$"));
    return user;
  }
}
