package tr.example.spring.security.keycloak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping("/getTest")
    public String getTest() {
        return "Hello";
    }

    @GetMapping("/getAdmin")
    public String getAdmin() {
        return "Hello Admin";
    }
}
