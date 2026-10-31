package com.example.babettahub.domain.blog.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record BlogPostResponse(
        Long id,
        Long authorId,
        Set<String> tags,
        String title,
        String content,
        LocalDateTime createdAt
) {
}
