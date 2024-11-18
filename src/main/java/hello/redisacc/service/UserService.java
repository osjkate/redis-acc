package hello.redisacc.service;

import hello.redisacc.Repository.ReviewRepository;
import hello.redisacc.Repository.UserRedisRepository;
import hello.redisacc.Repository.UserRepository;
import hello.redisacc.domain.Review;
import hello.redisacc.domain.User;
import hello.redisacc.dto.ScoreResponseDto;
import hello.redisacc.dto.UserCreateRequestDto;
import hello.redisacc.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRedisRepository userRedisRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void createUser(UserCreateRequestDto request) {
        User user = User.builder().username(request.getUsername()).build();
        userRepository.save(user);
    }

    @Transactional
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    @Transactional
    public UserResponseDto getById(Long id) {
        return UserResponseDto.toDto(userRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. ")));
    }

    @Transactional
    public UserResponseDto getByUsername(String username) {
        return UserResponseDto.toDto(findByUsername(username));
    }

    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. "));
    }

    @Transactional
    public ScoreResponseDto getTotalScore(String username) {
        User user = findByUsername(username);
        String isCached = "";

        int response = 0;
        Optional<Integer> totalScore = userRedisRepository.findById(user.getId());
        if (!totalScore.isPresent()) {
            List<Review> reviews = reviewRepository.findAllByUser(user);
            int sum = 0;
            for (Review review : reviews) response += review.getScore();
            userRedisRepository.save(user.getId(), response);
            isCached = "캐시 미스!!!!!";
        } else {
            response = totalScore.get();
            isCached = "캐시 히트!!!!";
        }

        return ScoreResponseDto.toDto(isCached, response);
    }
}
