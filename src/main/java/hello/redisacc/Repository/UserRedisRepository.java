package hello.redisacc.Repository;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRedisRepository {
    private final RedisTemplate<String, Integer> redisTemplate;
    private final static String USER_KEY = "USER_ID:";

    public void save(Long userId, Integer score) {
        redisTemplate.opsForValue().set(USER_KEY + userId, score);
    }
    public Optional<Integer> findById(Long userId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(USER_KEY + userId));
    }
}
