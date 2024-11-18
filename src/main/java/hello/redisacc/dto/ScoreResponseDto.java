package hello.redisacc.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScoreResponseDto {
    private String isCached;
    private int totalScore;

    public static ScoreResponseDto toDto(String isCached, int totalScore) {
        return ScoreResponseDto.builder()
                .isCached(isCached)
                .totalScore(totalScore)
                .build();
    }
}
