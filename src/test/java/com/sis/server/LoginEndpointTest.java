package com.sis.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sis.server.dto.LoginRequest;
import com.sis.server.dto.LoginResponse;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    classes = SisServerApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LoginEndpointTest {
  @LocalServerPort int port;
  @Autowired TestRestTemplate restTemplate;
  
  @Test
  void givenValidLoginData_whenLoginUser_thenReturnStatus201AndJwtToken() {
    // Given
    LoginRequest requestBody = new LoginRequest();
    requestBody.setUsername("username");
    requestBody.setPassword("password");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");

    HttpEntity<LoginRequest> request = new HttpEntity<>(requestBody, headers);

    // When
    ResponseEntity<LoginResponse> response =
        restTemplate.exchange(getUrl(), HttpMethod.POST, request, LoginResponse.class);

    // Then
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @NotNull
  private String getUrl() {
    return "http://localhost:" + port + "/auth/login";
  }
}
