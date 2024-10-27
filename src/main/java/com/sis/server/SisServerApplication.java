package com.sis.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sis.server")
public class SisServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SisServerApplication.class, args);
  }
}
