package com.sparta.backend.service;


import com.amazonaws.services.kms.model.NotFoundException;
import com.sparta.backend.S3.S3Uploader;
import com.sparta.backend.domain.Comment;
import com.sparta.backend.domain.Dibs;
import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.request.PostRequestDto;
import com.sparta.backend.dto.response.*;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.repository.CommentRepository;
import com.sparta.backend.repository.DibsRepository;
import com.sparta.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final S3Uploader s3Uploader;
    private final JwtTokenProvider jwtTokenProvider;
    private final DibsRepository dibsRepository;
    public ResponseDto<?> searchPosts() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<AllPostResponseDto> allPostResponseDtoList = new ArrayList<>();


        for (Post post : postList) {
            Long dibCount = dibsRepository.countByPost(post);
            Long commentsCount = commentRepository.countByPost(post);
            allPostResponseDtoList.add(
                    AllPostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .imgUrl(post.getImgUrl())
                            .price(post.getPrice())
                            .dibCount(dibCount)
                            .view(post.getViews())
                            .commentsCount(commentsCount)
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(allPostResponseDtoList);
    }


    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto, MultipartFile file, HttpServletRequest request) throws IOException {

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        // 로그인 검증
        String imgUrl = s3Uploader.uploadFiles(file, "static");

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .price(postRequestDto.getPrice())
                .imgUrl(imgUrl)
                .category(postRequestDto.getCategory())
                .views(0L)
                .member(member)
                .build();
        postRepository.save(post);
        return ResponseDto.success("게시글 게시 완료");
    }

    @Transactional
    public ResponseDto<?> getPostDetail(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }
        addViewCount(post.get());
        List<Comment> commentList = commentRepository.findAllByPost(post.get());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment comment : commentList) {
            commentResponseDtoList.add(
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
        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.get().getId())
                        .title(post.get().getTitle())
                        .content(post.get().getContent())
                        .price(post.get().getPrice())
                        .imgUrl(post.get().getImgUrl())
                        .category(post.get().getCategory())
                        .views(post.get().getViews())
                        .commentResponseDtoList(commentResponseDtoList)
                        .memberId(post.get().getMember().getMemberId())
                        .build()
        );

    }

    @Transactional
    public ResponseDto<?> getMemberPostDetail(Long postId, HttpServletRequest request){
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }
        boolean dibsResult;

        dibsResult = null != dibsRepository.findByMemberAndPost(member, post);

        addViewCount(post.get());
        List<Comment> commentList = commentRepository.findAllByPost(post.get());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment comment : commentList) {
            commentResponseDtoList.add(
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
        return ResponseDto.success(
                MemberPostResponseDto.builder()
                        .id(post.get().getId())
                        .title(post.get().getTitle())
                        .content(post.get().getContent())
                        .price(post.get().getPrice())
                        .imgUrl(post.get().getImgUrl())
                        .category(post.get().getCategory())
                        .dibsResult(dibsResult)
                        .views(post.get().getViews())
                        .commentResponseDtoList(commentResponseDtoList)
                        .memberId(post.get().getMember().getMemberId())
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
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Transactional
    public void addViewCount(Post post) {
        post.viewsAddCount();
        post.updateView(post);
    }
}
