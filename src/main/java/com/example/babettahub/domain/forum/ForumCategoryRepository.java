package com.example.babettahub.domain.forum;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Long> {

    //asi nebudem hladat kategorie
}
