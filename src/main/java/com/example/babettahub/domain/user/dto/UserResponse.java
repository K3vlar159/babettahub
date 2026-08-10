package com.example.babettahub.domain.user.dto;

import com.example.babettahub.domain.user.Role;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        String email,
        Role role,
        String bio,
        LocalDateTime createdAt
) {}