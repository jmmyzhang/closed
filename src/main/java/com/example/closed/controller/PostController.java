package com.example.closed.controller;

import com.example.closed.entity.Post;
import com.example.closed.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/by-user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable UUID userId) {
        return postService.getPostsByUserId(userId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Post> createPost(@PathVariable UUID userId, @RequestBody Post post) {
        try {
            Post newPost = postService.createPost(userId, post);
            return ResponseEntity.ok(newPost);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
