package tr.example.spring.security.jdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.example.spring.security.jdbc.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
