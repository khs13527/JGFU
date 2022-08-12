package com.sparta.backend.domain;

import com.sparta.backend.test.TestMemberCreateDto;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Dibs> dibsSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Post> postSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Comment> commentSet = new HashSet<>();

    //테스트 유저 생성 메소드
    public Member(TestMemberCreateDto requestDto){
        this.memberId = requestDto.getMemberId();
        this.password = requestDto.getPassword();
    }
}
