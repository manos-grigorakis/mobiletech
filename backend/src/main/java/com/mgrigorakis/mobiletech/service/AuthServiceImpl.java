package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.exception.ConflictException;
import com.mgrigorakis.mobiletech.dto.LoginRequest;
import com.mgrigorakis.mobiletech.dto.LoginResponse;
import com.mgrigorakis.mobiletech.dto.RegisterRequest;
import com.mgrigorakis.mobiletech.dto.RegisterResponse;
import com.mgrigorakis.mobiletech.model.Role;
import com.mgrigorakis.mobiletech.model.User;
import com.mgrigorakis.mobiletech.model.UserInfoDetails;
import com.mgrigorakis.mobiletech.repository.RoleRepository;
import com.mgrigorakis.mobiletech.repository.UserRepository;
import com.mgrigorakis.mobiletech.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserInfoDetails userDetails = (UserInfoDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        String roleName = user.getRole().getName();
        String token = jwtService.generateJwtToken(user.getEmail(),  roleName);

        return new LoginResponse(token);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.findByEmail(request.email()).isPresent()) {
            throw new ConflictException("User already exists", "USER_ALREADY_EXISTS");
        }

        Role role = roleRepository.findByName("CUSTOMER").orElseThrow(() -> {
            log.warn("Registration: CUSTOMER role not found");
            return new RuntimeException("Please contact the administrator");
        });

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(encodedPassword)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);
        return new RegisterResponse(savedUser.getId(),  savedUser.getEmail());
    }
}
