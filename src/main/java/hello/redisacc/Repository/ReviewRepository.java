package hello.redisacc.Repository;

import hello.redisacc.domain.Review;
import hello.redisacc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUser(User user);
}
