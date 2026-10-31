package com.example.babettahub.domain.blog;

import com.example.babettahub.common.ResourceNotFoundException;
import com.example.babettahub.domain.blog.dto.BlogPostRequest;
import com.example.babettahub.domain.blog.dto.BlogPostResponse;
import com.example.babettahub.domain.user.User;
import com.example.babettahub.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public BlogPostResponse getById(Long blogPostId){

        BlogPost blogPost = blogPostRepository.findById(blogPostId).orElseThrow(() -> new ResourceNotFoundException("Článok s ID " + blogPostId + " neexistuje"));

        return mapToResponse(blogPost);
    }

    @Transactional(readOnly = true)
    public List<BlogPostResponse> getAllPublished() {
        return blogPostRepository.findByStatus(BlogStatus.PUBLISHED).stream().map(BlogPostService::mapToResponse).toList();
    }

    @Transactional
    public BlogPostResponse create(BlogPostRequest request) {

        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("User neexistuje"));

        Set<Tag> tags = resolveTags(request.tagIds());

        BlogPost post = new BlogPost();
        post.setAuthor(author);
        post.setTags(tags);
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setStatus(BlogStatus.PUBLISHED);
        post.setCreatedAt(LocalDateTime.now());

        BlogPost savedPost = blogPostRepository.save(post);
        return mapToResponse(savedPost);

    }

    private @NonNull Set<Tag> resolveTags(Set<Long> tagIds) {
        Set<Long> ids = tagIds != null ? tagIds : Set.of();
        return new HashSet<>(tagRepository.findAllById(ids));
    }

    @Transactional
    public BlogPostResponse update(Long id, BlogPostRequest request){
        BlogPost post = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Článok s ID " + id +  " neexistuje"));

        Set<Tag> tags = resolveTags(request.tagIds());

        post.setTags(tags);
        post.setTitle(request.title());
        post.setContent(request.content());

        BlogPost savedPost = blogPostRepository.save(post);

        return mapToResponse(savedPost);
    }

    @Transactional
    public BlogPostResponse archive(Long id){
        BlogPost post = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Článok s ID " + id +  " neexistuje"));

        post.setStatus(BlogStatus.ARCHIVED);

        BlogPost savedPost = blogPostRepository.save(post);

        return mapToResponse(savedPost);
    }

    private static BlogPostResponse mapToResponse(BlogPost post) {
        return new BlogPostResponse(
                post.getId(),
                post.getAuthor().getId(),
                post.getTags().stream().map(Tag::getName).collect(Collectors.toSet()),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt()
        );
    }

}
