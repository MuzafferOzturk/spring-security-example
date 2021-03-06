package tr.example.spring.security.jdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.example.spring.security.jdbc.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
}
