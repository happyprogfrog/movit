package com.happyprogfrog.movit.dto.user.request;

import com.happyprogfrog.movit.model.User;
import jakarta.validation.constraints.NotBlank;

public record UserReqAddDto(
        @NotBlank
        String email,
        @NotBlank
        String nickname,
        @NotBlank
        String password
) {
        public User toEntity(String password) {
                return new User(
                        this.email,
                        this.nickname,
                        password
                );
        }
}
