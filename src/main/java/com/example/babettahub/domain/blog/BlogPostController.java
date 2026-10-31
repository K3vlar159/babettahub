package com.example.babettahub.domain.blog;


import com.example.babettahub.domain.blog.dto.BlogPostRequest;
import com.example.babettahub.domain.blog.dto.BlogPostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @GetMapping("/{id}")
    public BlogPostResponse getById(@PathVariable Long id){
        return blogPostService.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostResponse create(@Valid @RequestBody BlogPostRequest request){
        return blogPostService.create(request);
    }

    @GetMapping()
    public List<BlogPostResponse> getAllPublished(){
        return blogPostService.getAllPublished();
    }

    @PutMapping("/{id}")
    public BlogPostResponse update(@PathVariable Long id, @Valid @RequestBody BlogPostRequest request) {
        return blogPostService.update(id,request);
    }

    @PatchMapping("/{id}/archive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void archive(@PathVariable Long id){
        blogPostService.archive(id);
    }
}
