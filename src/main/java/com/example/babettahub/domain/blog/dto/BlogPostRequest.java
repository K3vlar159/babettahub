package com.example.babettahub.domain.blog.dto;

import jakarta.validation.constraints.*;

import java.util.Set;

public record BlogPostRequest(

        @NotBlank(message = "Nadpis nesmie byť prázdny")
        @Size(min = 3, max = 30, message = "Nadpis musí mať 3-30 znakov")
        String title,

        @Size( max = 10000, message = "Obsah musí mať max. 10000 znakov")
        String content,

        Set<Long> tagIds,

        @NotNull
        Long userId

) {}