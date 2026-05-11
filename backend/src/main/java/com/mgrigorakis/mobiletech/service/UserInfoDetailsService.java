package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.model.User;
import com.mgrigorakis.mobiletech.model.UserInfoDetails;
import com.mgrigorakis.mobiletech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserInfoDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()) throw new UsernameNotFoundException("Invalid credentials");

        return new UserInfoDetails(user.get());
    }
}
