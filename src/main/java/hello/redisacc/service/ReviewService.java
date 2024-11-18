package hello.redisacc.service;

import hello.redisacc.Repository.ReviewRedisRepository;
import hello.redisacc.Repository.ReviewRepository;
import hello.redisacc.domain.Review;
import hello.redisacc.dto.ReviewCreateRequestDto;
import hello.redisacc.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewRedisRepository reviewRedisRepository;
    private final UserService userService;

    @Transactional
    public void createReview(ReviewCreateRequestDto request) {
        if (reviewRedisRepository.size() == 2) {
            List<ReviewCreateRequestDto> requests = reviewRedisRepository.findAll();
            requests.add(request);
            List<Review> reviews = new ArrayList<>();
            for (ReviewCreateRequestDto requestDto : requests) {
                reviews.add(requestDto.toEntity(userService.findByUsername(requestDto.getUsername())));
            }
            reviewRedisRepository.deleteAll();
            reviewRepository.saveAll(reviews);
        } else {
            reviewRedisRepository.save(request);
        }
    }

    // DB에서 전체 조회
    public List<ReviewResponseDto> findAll() {
        return reviewRepository.findAll().stream().map(ReviewResponseDto::toDto).toList();

    }

    // 아이디로 조회
    public ReviewResponseDto findReviewById(Long id) {
        return ReviewResponseDto.toDto(reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 조회할 수 없습니다.")));
    }

    // 유저의 모든 리뷰 조회
    public List<Review> findAllReviewsByUsername(String username) {
        return reviewRepository.findAllByUser(userService.findByUsername(username));
    }

    @Transactional
    public void deleteRedisReview() {
        reviewRedisRepository.deleteAll();
    }
}
