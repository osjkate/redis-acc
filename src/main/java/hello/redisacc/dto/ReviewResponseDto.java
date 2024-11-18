package hello.redisacc.dto;

import hello.redisacc.domain.Review;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewResponseDto {
    private Long id;
    private Long score;

    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .score(review.getScore())
                .build();
    }
}
