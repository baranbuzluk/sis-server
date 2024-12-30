package com.sis.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sis.server.dto.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest(
    classes = SisServerApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginEndpointTest {

  public static final String URL = "http://localhost:8080/auth/login";
  @Autowired TestRestTemplate restTemplate;

  private void performLoginTest(String username, String password) {
    LoginRequest requestBody = new LoginRequest();
    requestBody.setUsername(username);
    requestBody.setPassword(password);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<LoginRequest> request = new HttpEntity<>(requestBody, headers);

    // When
    ResponseEntity<String> result =
        restTemplate.exchange(URL, HttpMethod.POST, request, String.class);

    // Then
    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  @DisplayName("Student Login Operation")
  void shouldReturnJWTToken_whenValidStudentCredentialsAreProvided() {
    String studentNumber = "1";
    String password = "Example1!2024";
    performLoginTest(studentNumber, password);
  }

  @Test
  @DisplayName("Teacher Login Operation")
  void shouldReturnJWTToken_whenValidTeacherCredentialsAreProvided() {
    String mail = "teacher@school.edu";
    String password = "Secure$Pass123";
    performLoginTest(mail, password);
  }

  @Test
  @DisplayName("Admin Login Operation")
  void shouldReturnJWTToken_whenValidAdminCredentialsAreProvided() {
    String mail = "admin@school.edu";
    performLoginTest(mail, "NewYear#2024$");
  }
}
