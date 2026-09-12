package com.example.babettahub.domain.forum;

import com.example.babettahub.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "forum_posts")
@Getter
@Setter
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "thread_id",nullable = false)
    private ForumThread thread;

    @ManyToOne
    @JoinColumn(name = "parent_post", nullable = true)
    private ForumPost parentPost;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
