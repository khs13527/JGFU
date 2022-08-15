package com.sparta.backend.repository;

import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByPostSet(Post post);
    Optional<Member> findById(Long id);
    Optional<Member> findByMemberId(String memberId);

}
