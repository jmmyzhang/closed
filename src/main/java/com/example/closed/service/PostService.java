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

    public Post createPost(User user, Post post) {
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public List<Post> getPostsByUserId(UUID userId) {
        return postRepository.findByUserUserId(userId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
