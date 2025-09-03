package com.example.closed.service;

import com.example.closed.entity.Post;
import com.example.closed.entity.User;
import com.example.closed.repository.PostRepository;
import com.example.closed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(UUID userId, Post post) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            post.setUser(userOptional.get());
            post.setCreatedAt(LocalDateTime.now());
            return postRepository.save(post);
        } else {
            throw new RuntimeException("User not found: " + userId);
        }
    }

    public List<Post> getPostsByUserId(UUID userId) {
        return postRepository.findByUserUserId(userId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
