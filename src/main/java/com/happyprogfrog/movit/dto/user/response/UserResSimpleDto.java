package com.happyprogfrog.movit.dto.user.response;

import com.happyprogfrog.movit.dto.user.UserDefaultDto;

public record UserResSimpleDto(
        Long userId,
        String email,
        String nickname
) {

    public UserResSimpleDto(UserDefaultDto defaultDto) {
        this(
                defaultDto.userId(),
                defaultDto.email(),
                defaultDto.nickname()
        );
    }
}
