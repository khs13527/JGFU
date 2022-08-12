package com.sparta.backend.repository;

import com.sparta.backend.domain.Comment;
import com.sparta.backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Long countByPost(Post post);
}
