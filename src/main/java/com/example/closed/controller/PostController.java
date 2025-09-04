package com.example.closed.controller;

import com.example.closed.entity.Post;
import com.example.closed.entity.User;
import com.example.closed.repository.UserRepository;
import com.example.closed.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/by-user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable UUID userId) {
        return postService.getPostsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userRepository.findByUsername(currentUsername);

        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Post newPost = postService.createPost(user, post);
        return ResponseEntity.ok(newPost);
    }
}
