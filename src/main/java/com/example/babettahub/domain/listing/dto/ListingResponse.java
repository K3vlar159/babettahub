package com.example.babettahub.domain.listing.dto;

import com.example.babettahub.domain.listing.ListingStatus;

import java.time.LocalDateTime;

public record ListingResponse(

        Long id,
        Long sellerId,
        String sellerUsername,
        String title,
        String description,
        Integer price,
        String contactNumber,
        String contactEmail,
        ListingStatus status,
        LocalDateTime createdAt
) {
}
