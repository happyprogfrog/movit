package com.happyprogfrog.movit.dto.movie.request;

import jakarta.validation.constraints.NotBlank;

public record MovieReqUpdateDto(
        @NotBlank
        String title,
        String director,
        String summary,
        String imageUrl
) {
    // something to do if you need
}
