package com.example.babettahub.domain.forum;

import com.example.babettahub.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumThreadRepository extends JpaRepository<ForumThread, Long> {

    List<ForumThread> findByAuthor(User author);
    List<ForumThread> findByCategory(ForumCategory category);

    //findByLocked ? findByPinned? findByTitle?
}
