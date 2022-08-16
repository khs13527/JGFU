package com.sparta.backend.repository;

import com.sparta.backend.domain.Dibs;
import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllByMember(Member member);
    List<Post> findByCategoryOrderByCreatedAtDesc(String category);

    Post findDibsPostById(Long id);
}
