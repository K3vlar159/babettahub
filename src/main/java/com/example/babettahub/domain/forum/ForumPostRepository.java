package com.example.babettahub.domain.forum;

import com.example.babettahub.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {

    List<ForumPost> findByAuthor(User author);
    List<ForumPost> findByThread(ForumThread thread);
}
