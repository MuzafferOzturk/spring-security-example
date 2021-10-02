package tr.example.spring.security.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.example.spring.security.jdbc.model.User;
import tr.example.spring.security.jdbc.model.UserDetailsImpl;
import tr.example.spring.security.jdbc.repository.UserRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException(name));
        return UserDetailsImpl.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .password(user.getPassword())
                .roleList(new ArrayList<>(user.getRoleList())).build();
    }
}
