package tr.example.spring.security.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tr.example.spring.security.jdbc.model.Role;
import tr.example.spring.security.jdbc.model.User;
import tr.example.spring.security.jdbc.repository.UserRepository;
import tr.example.spring.security.jdbc.model.RoleEnum;
import tr.example.spring.security.jdbc.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    private List<Role> createRoles() {
        List<Role> roleList = Arrays.stream(RoleEnum.values()).map(Role::new).collect(Collectors.toList());
        roleList.forEach(roleRepository::save);
        return roleList;
    }

    private User createUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword(passwordEncoder.encode("password"));
        List<Role> roleList = createRoles();
        user.setRoleList(roleList);
        userRepository.save(user);
        return user;
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            createUser();
        };
    }
}
