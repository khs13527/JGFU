package com.sparta.backend.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Post post;
}
