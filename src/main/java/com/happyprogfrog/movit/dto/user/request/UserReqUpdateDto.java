package com.happyprogfrog.movit.dto.user.request;

import com.happyprogfrog.movit.model.User;
import jakarta.validation.constraints.NotBlank;

public record UserReqUpdateDto(
        @NotBlank
        String nickname,
        @NotBlank
        String password
) {
        // something to do if you need
}
