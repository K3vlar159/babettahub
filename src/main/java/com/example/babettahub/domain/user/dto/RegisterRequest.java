package com.example.babettahub.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Username nesmie byť prázdny")
        @Size(min = 3, max = 30, message = "Username musí mať 3-30 znakov")
        String username,

        @NotBlank(message = "Email nesmie byť prázdny")
        @Email(message = "Email nie je platný")
        String email,

        @NotBlank(message = "Heslo nesmie byť prázdne")
        @Size(min = 6, message = "Heslo musí mať aspoň 6 znakov")
        String password

) {}