package com.example.babettahub.domain.blog.dto;

import jakarta.validation.constraints.*;

import java.util.Set;

public record BlogPostRequest(

        @NotBlank(message = "Nadpis nesmie byť prázdny")
        @Size(min = 3, max = 150, message = "Nadpis musí mať 3-150 znakov")
        String title,

        @NotBlank(message = "Obsah nesmie byť prázdny")
        @Size( max = 10000, message = "Obsah musí mať max. 10000 znakov")
        String content,

        Set<Long> tagIds,

        @NotNull
        Long authorId

) {}