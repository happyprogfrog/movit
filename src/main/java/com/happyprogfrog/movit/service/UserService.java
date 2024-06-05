package com.happyprogfrog.movit.service;

import com.happyprogfrog.movit.dto.user.UserDefaultDto;
import com.happyprogfrog.movit.dto.user.request.UserReqAddDto;
import com.happyprogfrog.movit.dto.user.request.UserReqUpdateDto;
import com.happyprogfrog.movit.exception.ResourceNotFoundException;
import com.happyprogfrog.movit.model.User;
import com.happyprogfrog.movit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 사용자 ID로 조회
     */
    @Transactional(readOnly = true)
    public UserDefaultDto getUserById(Long userId) {
        User user = findUserById(userId);
        return new UserDefaultDto(user);
    }

    /**
     * 새로운 사용자 생성
     */
    public UserDefaultDto createUser(UserReqAddDto reqAddDto) {
        User user = reqAddDto.toEntity(passwordEncoder.encode(reqAddDto.password()));
        userRepository.save(user);
        return new UserDefaultDto(user);
    }

    /**
     * 이메일과 비밀번호로 사용자 정보 조회
     */
    @Transactional(readOnly = true)
    public UserDefaultDto getUserByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new UserDefaultDto(user);
            }
        }

        return null;
    }

    /**
     * 사용자 정보를 업데이트
     */
    public UserDefaultDto updateUser(Long userId, UserReqUpdateDto reqUpdateDto) {
        User user = findUserById(userId);
        user.updateEntity(reqUpdateDto.nickname(), passwordEncoder.encode(reqUpdateDto.password()));
        return new UserDefaultDto(user);
    }

    /**
     * 사용자 정보를 삭제
     */
    public void deleteUser(Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }
}
