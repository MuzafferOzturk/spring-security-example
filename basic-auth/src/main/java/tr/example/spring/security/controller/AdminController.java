package tr.example.spring.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello admin";
    }

    @RolesAllowed("ADMIN_V2")
    @GetMapping("/helloV2")
    public String helloV2() {
        return "Hello admin";
    }

    @Secured("ROLE_ADMIN_V2")
    @GetMapping("/helloV3")
    public String helloV3() {
        return "Hello admin";
    }

    @PreAuthorize("hasRole('ADMIN_V2')")
    @GetMapping("/helloV4")
    public String helloV4() {
        return "Hello admin";
    }

    @PostAuthorize("returnObject == 'PostAuth'")
    @GetMapping("/helloV5")
    public String helloV5() {
        return "PostAuth";
    }
}
