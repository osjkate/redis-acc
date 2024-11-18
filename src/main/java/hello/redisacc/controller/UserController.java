package hello.redisacc.controller;

import hello.redisacc.dto.UserCreateRequestDto;
import hello.redisacc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "유저 회원가입")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @RequestBody UserCreateRequestDto request) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 유저 조회하기")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "유저 아이디로 조회하기")
    @GetMapping
    public ResponseEntity<?> getUserById(@RequestParam long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(summary = "유저 총점수 조회하기")
    @GetMapping("/score")
    public ResponseEntity<?> getTotalScore(@RequestParam String username) {
        return ResponseEntity.ok(userService.getTotalScore(username));
    }
}
