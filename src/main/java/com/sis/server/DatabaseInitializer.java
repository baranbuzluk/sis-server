package com.sis.server;

import com.sis.server.entity.User;
import com.sis.server.entity.UserType;
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
    User student = createUser("1", UserType.STUDENT);
    User teacher = createUser("teacher@school.edu", UserType.TEACHER);
    User admin = createUser("admin@school.edu", UserType.TEACHER);
    userRepository.saveAllAndFlush(List.of(student, teacher, admin));
    log.info("Database initialized.");
  }

  private User createUser(String username, UserType type) {
    User user = new User();
    user.setUsername(username);
    user.setUserType(type);
    user.setPassword(passwordEncoder.encode("Mypassw0rd!"));
    return user;
  }
}
