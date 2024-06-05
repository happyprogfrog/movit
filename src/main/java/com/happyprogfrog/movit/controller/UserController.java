package com.happyprogfrog.movit.controller;

import com.happyprogfrog.movit.dto.user.UserDefaultDto;
import com.happyprogfrog.movit.dto.user.request.UserReqAddDto;
import com.happyprogfrog.movit.dto.user.request.UserReqLoginDto;
import com.happyprogfrog.movit.dto.user.request.UserReqUpdateDto;
import com.happyprogfrog.movit.dto.user.response.UserResSimpleDto;
import com.happyprogfrog.movit.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자 ID로 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResSimpleDto> getUserById(@PathVariable Long userId) {
        UserResSimpleDto resSimpleDto = new UserResSimpleDto(userService.getUserById(userId));
        return new ResponseEntity<>(resSimpleDto, HttpStatus.OK);
    }

    /**
     * 새로운 사용자 추가
     */
    @PostMapping("")
    public ResponseEntity<UserResSimpleDto> addUser(@Valid @RequestBody UserReqAddDto reqAddDto) {
        UserResSimpleDto resSimpleDto = new UserResSimpleDto(userService.createUser(reqAddDto));
        return new ResponseEntity<>(resSimpleDto, HttpStatus.CREATED);
    }

    /**
     * 사용자 정보 업데이트
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResSimpleDto> updateUser(@PathVariable Long userId,
                                                       @Valid @RequestBody UserReqUpdateDto reqUpdateDto) {
        UserResSimpleDto resSimpleDto = new UserResSimpleDto(userService.updateUser(userId, reqUpdateDto));
        return new ResponseEntity<>(resSimpleDto, HttpStatus.OK);
    }

    /**
     * 사용자 정보 삭제
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 사용자 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<UserResSimpleDto> login(@RequestBody UserReqLoginDto reqLoginDto) {
        UserDefaultDto defaultDto = userService.getUserByEmailAndPassword(reqLoginDto.email(), reqLoginDto.password());
        if (defaultDto != null) {
            UserResSimpleDto resSimpleDto = new UserResSimpleDto(defaultDto);
            return new ResponseEntity<>(resSimpleDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
