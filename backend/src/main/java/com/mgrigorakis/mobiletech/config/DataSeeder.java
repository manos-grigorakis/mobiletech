package com.mgrigorakis.mobiletech.config;

import com.mgrigorakis.mobiletech.model.Role;
import com.mgrigorakis.mobiletech.model.User;
import com.mgrigorakis.mobiletech.repository.RoleRepository;
import com.mgrigorakis.mobiletech.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
public class DataSeeder {
    @Value("${app.accounts.admin.email}")
    private String adminEmail;

    @Value("${app.accounts.admin.password}")
    private String adminPassword;

    @Value("${app.accounts.demo.email}")
    private String demoAdminEmail;

    @Value("${app.accounts.demo.password}")
    private String demoAdminPassword;

    @Bean
    CommandLineRunner seedRoles(
            RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            seedRoles(roleRepository);
            seedAdminUser(roleRepository, userRepository, passwordEncoder);
            seedDemoAdminUser(roleRepository, userRepository, passwordEncoder);
        };
    }

    private void seedRoles(RoleRepository roleRepository) {
        List.of("ADMIN", "MANAGER", "CUSTOMER", "DEMO").forEach(roleName -> {
            if (roleRepository.findByName(roleName).isEmpty()) {
                roleRepository.save(Role.builder().name(roleName).build());
                log.info("Seeded role: {}", roleName);
            }
        });
    }

    private void seedAdminUser(
            RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        if(userRepository.findByEmail(adminEmail).isEmpty()) {
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

            String encodedPassword = passwordEncoder.encode((adminPassword));

            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("User")
                    .email(adminEmail)
                    .password(encodedPassword)
                    .role(adminRole)
                    .build();

            userRepository.save(admin);
            log.info("Seeded admin user: {}", adminEmail);
        }
    }

    private void seedDemoAdminUser(
            RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        if (userRepository.findByEmail(demoAdminEmail).isEmpty()) {
            Role demoRole = roleRepository.findByName("DEMO")
                    .orElseThrow(() -> new RuntimeException("DEMO role not found"));

            String encodedPassword = passwordEncoder.encode((demoAdminPassword));

            User demo = User.builder()
                    .firstName("Demo")
                    .lastName("User")
                    .email(demoAdminEmail)
                    .password(encodedPassword)
                    .role(demoRole)
                    .build();

            userRepository.save(demo);
            log.info("Seeded demo user: {}", demoAdminEmail);
        }
    }
}
