package com.happyprogfrog.movit.dto.movie.response;

import com.happyprogfrog.movit.dto.movie.MovieDefaultDto;

public record MovieResSimpleDto(
        Long movieId,
        String title,
        String imageUrl
) {

    public MovieResSimpleDto(MovieDefaultDto defaultDto) {
        this(
                defaultDto.movieId(),
                defaultDto.title(),
                defaultDto.imageUrl()
        );
    }
}
