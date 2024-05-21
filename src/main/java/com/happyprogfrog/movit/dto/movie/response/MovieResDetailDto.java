package com.happyprogfrog.movit.dto.movie.response;

import com.happyprogfrog.movit.dto.movie.MovieDefaultDto;

public record MovieResDetailDto(
        Long movieId,
        String title,
        String director,
        String summary,
        String imageUrl
) {

    public MovieResDetailDto(MovieDefaultDto defaultDto) {
        this(
                defaultDto.movieId(),
                defaultDto.title(),
                defaultDto.director(),
                defaultDto.summary(),
                defaultDto.imageUrl()
        );
    }
}
