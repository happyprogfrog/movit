package com.happyprogfrog.movit.dto.user;

import com.happyprogfrog.movit.model.User;

import java.time.LocalDateTime;

public record UserDefaultDto(
        Long userId,
        String email,
        String nickname,
        String password,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public UserDefaultDto(User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
