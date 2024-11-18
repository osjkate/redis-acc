package hello.redisacc.controller;

import hello.redisacc.domain.Review;
import hello.redisacc.dto.ReviewCreateRequestDto;
import hello.redisacc.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "리뷰 API")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create")
    @Operation(summary = "리뷰 생성하기")
    public ResponseEntity<?> createReview(@RequestBody ReviewCreateRequestDto request) {
        reviewService.createReview(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 리뷰 조회하기")
    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    @Operation(summary = "리뷰 아이디로 조회")
    @GetMapping
    public ResponseEntity<?> getReviewById(@RequestParam Long id) {
        return ResponseEntity.ok(reviewService.findReviewById(id));
    }

    @Operation(summary = "유저의 모든 리뷰 조회하기")
    @GetMapping("/users")
    public ResponseEntity<?> getAllReviewsByUsername(@RequestParam String username) {
        return ResponseEntity.ok(reviewService.findAllReviewsByUsername(username));
    }

    @Operation(summary = "레디스 초기화")
    @DeleteMapping("/redis")
    public ResponseEntity<?> deleteReview() {
        reviewService.deleteRedisReview();
        return ResponseEntity.noContent().build();
    }
}
