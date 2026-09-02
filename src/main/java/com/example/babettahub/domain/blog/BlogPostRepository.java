package com.example.babettahub.domain.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository <BlogPost, Long> {

    List<BlogPost> findByAuthorId(Long id);
    List <BlogPost> findByStatus(BlogStatus status);
}
