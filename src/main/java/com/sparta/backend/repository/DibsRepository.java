package com.sparta.backend.repository;

import com.sparta.backend.domain.Dibs;
import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DibsRepository extends JpaRepository<Dibs,Long> {
    Long countByPost(Post post);
    List<Dibs> findAllByMember(Member member);
    Optional<Dibs> findByPost(Post post);
    void deleteByPostAndMember(Post post, Member member);

    Dibs findByMemberAndPost(Member member, Optional<Post> post);
}
