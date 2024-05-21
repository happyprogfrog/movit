package com.happyprogfrog.movit.dto.movie.request;

import com.happyprogfrog.movit.model.Movie;
import jakarta.validation.constraints.NotBlank;

public record MovieReqAddDto(
        @NotBlank
        String title,
        String director,
        String summary,
        String imageUrl
) {

    public Movie toEntity() {
        return new Movie(
                this.title,
                this.director,
                this.summary,
                this.imageUrl
        );
    }
}
