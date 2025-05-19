package com.dinlurceis.smartmed.config;

import com.dinlurceis.smartmed.domain.UserRole;
import com.dinlurceis.smartmed.model.User;
import com.dinlurceis.smartmed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if (!userRepository.existsByEmail("admininit@gmail.com")){
                var roles = new HashSet<String>();
                roles.add(UserRole.ADMIN.name());

                User user = User.builder()
                        .email("admininit@gmail.com")
                        .password(passwordEncoder.encode("12345678"))
                        .fullName("Admin")
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("Admin user has been created with default info (email: admininit@gmail.com, password: 12345678)");
            }
        };
    }
}
