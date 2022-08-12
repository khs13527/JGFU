package com.sparta.backend.repository;

import com.sparta.backend.domain.Dibs;
import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllByMember(Member member);
    Post findByDibsSet(Dibs dibs);
}
