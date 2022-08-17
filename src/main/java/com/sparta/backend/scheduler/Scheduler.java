package com.sparta.backend.scheduler;

import com.amazonaws.services.s3.AmazonS3Client;
import com.sparta.backend.domain.Post;
import com.sparta.backend.repository.PostRepository;
import com.sparta.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
public class Scheduler {

    @Value("${bucketName}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private final PostRepository postRepository;
    private final PostService postService;
//    private final NaverShopSearch naverShopSearch;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 1 * * *")
    public void updateIsDoneImage() {
        // 저장된 모든 상품을 조회합니다.
        List<Post> postList = postRepository.findAll();
        for (Post p : postList) {
            // i 번째 상품을 꺼냅니다.
            //만약 판매완료 상태면 이미지 파일을 삭제합니다.
            if (p.getIsDone()) {
                String key = p.getImgUrl().substring("https://mysparta00.s3.ap-northeast-2.amazonaws.com/".length());
                amazonS3Client.deleteObject(bucket, key);
            }
        }
    }
}