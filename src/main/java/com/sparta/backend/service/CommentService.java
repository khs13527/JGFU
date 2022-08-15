package com.sparta.backend.service;

import com.amazonaws.Response;
import com.sparta.backend.domain.Comment;
import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.request.CommentRequestDto;
import com.sparta.backend.dto.response.CommentResponseDto;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PostService postService;

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto commentRequestDto, Long postId, HttpServletRequest request){
        // 로그인 검증
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content(commentRequestDto.getContent())
                .isEditMode(false)
                .build();
        commentRepository.save(comment);
        return ResponseDto.success(
                CommentResponseDto.builder()
                .id(comment.getId())
                .memberId(comment.getMember().getMemberId())
                .content(comment.getContent())
                .isEditMode(comment.getIsEditMode())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .postId(comment.getPost().getId())
                .build()
        );
    }

    @Transactional
    public ResponseDto<?> updateComment(CommentRequestDto commentRequestDto, Long postId, Long commentId, HttpServletRequest request){
        // 로그인 검증
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Comment comment = isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (comment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        comment.update(commentRequestDto);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .memberId(comment.getMember().getMemberId())
                        .content(comment.getContent())
                        .isEditMode(comment.getIsEditMode())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .postId(comment.getPost().getId())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long postId, Long commentId, HttpServletRequest request){
        //로그인 검증
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Comment comment = isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (comment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        commentRepository.delete(comment);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .memberId(comment.getMember().getMemberId())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .postId(comment.getPost().getId())
                        .build()
        );
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return null;
        }
        return jwtTokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }
}
