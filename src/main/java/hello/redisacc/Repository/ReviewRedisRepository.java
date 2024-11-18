package hello.redisacc.Repository;

import hello.redisacc.dto.ReviewCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReviewRedisRepository {
    private final RedisTemplate<String, ReviewCreateRequestDto> redisTemplate;
    private static final String REVIEW_KEY = "reviews";

    public void save(ReviewCreateRequestDto request) {
        String reviewId = UUID.randomUUID().toString();
        redisTemplate.opsForList().rightPush(REVIEW_KEY, request);
    }

    public List<ReviewCreateRequestDto> findAll() {
        return redisTemplate.opsForList().range(REVIEW_KEY, 0, -1);
    }

    public Long size() {
        return redisTemplate.opsForList().size(REVIEW_KEY);
    }

    public void deleteAll() {
        redisTemplate.delete(REVIEW_KEY);
    }
}
