package com.example.babettahub.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username nesmie byť prázdny")
        String username,

        @NotBlank(message = "Heslo nesmie byť prázdne")
        String password
) {
}
