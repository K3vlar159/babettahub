package com.example.babettahub.domain.user;

import com.example.babettahub.common.ConflictException;
import com.example.babettahub.common.InvalidCredentialsException;
import com.example.babettahub.domain.user.dto.LoginRequest;
import com.example.babettahub.domain.user.dto.LoginResponse;
import com.example.babettahub.domain.user.dto.RegisterRequest;
import com.example.babettahub.domain.user.dto.UserResponse;
import com.example.babettahub.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email je už obsadený");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new ConflictException("Username je už obsadený");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getRole(),
                saved.getBio(),
                saved.getCreatedAt()
        );
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new InvalidCredentialsException("Nesprávne meno alebo heslo"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Nesprávne meno alebo heslo");
        }

        return new LoginResponse(jwtService.generateToken(user.getUsername()));

    }
}