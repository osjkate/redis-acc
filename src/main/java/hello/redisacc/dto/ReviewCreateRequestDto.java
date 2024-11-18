package hello.redisacc.dto;

import hello.redisacc.domain.Review;
import hello.redisacc.domain.User;
import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {
    private String username;
    private Long score;

    public Review toEntity(User user) {
        return Review.builder()
                .user(user)
                .score(score)
                .build();
    }
}
