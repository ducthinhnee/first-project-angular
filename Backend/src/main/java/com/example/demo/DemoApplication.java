package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Job Search Platform API", version = "v1", description = "APIs for Job Search Platform"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class DemoApplication {

  @Value("${NAME:World}")
  String name;

  @RestController
  static class HelloworldController {
    @GetMapping("/")
    void hello(HttpServletResponse response) throws IOException {
      response.sendRedirect("/swagger-ui/index.html");
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
