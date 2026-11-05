package com.example.babettahub.domain.user;

import com.example.babettahub.domain.user.dto.LoginRequest;
import com.example.babettahub.domain.user.dto.LoginResponse;
import com.example.babettahub.domain.user.dto.RegisterRequest;
import com.example.babettahub.domain.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){
        return userService.login(request);
    }
}