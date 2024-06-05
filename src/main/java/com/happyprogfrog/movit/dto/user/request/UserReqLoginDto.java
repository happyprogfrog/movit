package com.happyprogfrog.movit.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserReqLoginDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
