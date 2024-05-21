package com.happyprogfrog.movit.dto.movie;

import com.happyprogfrog.movit.model.Movie;

import java.time.LocalDateTime;

public record MovieDefaultDto(
        Long movieId,
        String title,
        String director,
        String summary,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public MovieDefaultDto(Movie movie) {
        this(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getSummary(),
                movie.getImageUrl(),
                movie.getCreatedAt(),
                movie.getUpdatedAt()
        );
    }
}
