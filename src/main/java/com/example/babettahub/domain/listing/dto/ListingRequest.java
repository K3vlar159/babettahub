package com.example.babettahub.domain.listing.dto;

import jakarta.validation.constraints.*;

public record ListingRequest(

        @NotBlank(message = "Nadpis nesmie byť prázdny")
        @Size(min = 3, max = 30, message = "Nadpis musí mať 3-30 znakov")
        String title,

        @Size( max = 500, message = "Popis musí mať max. 500 znakov")
        String description,

        @Positive
        Integer price,

        String contactNumber,

        @Email(message = "Email nie je platný")
        String contactEmail,

        @NotNull
        Long userId

) {}
