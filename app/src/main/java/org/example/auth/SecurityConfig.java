package org.example.auth;

import lombok.Data;
import org.example.repository.UserRepository;
import org.example.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
@Data
public class SecurityConfig {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserDetailServiceImpl userDetailService;

    @Bean
    @Autowired
    public UserDetailServiceImpl userDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return new UserDetailServiceImpl(userRepository, passwordEncoder);
    }


}
