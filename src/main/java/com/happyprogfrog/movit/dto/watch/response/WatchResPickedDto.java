package com.happyprogfrog.movit.dto.watch.response;

import com.happyprogfrog.movit.dto.watch.WatchDefaultDto;

public record WatchResPickedDto(
        Long movieId,
        String title,
        String director,
        String imageUrl,
        boolean isPicked
) {

    public WatchResPickedDto(WatchDefaultDto defaultDto) {
        this(
                defaultDto.movie().getId(),
                defaultDto.movie().getTitle(),
                defaultDto.movie().getDirector(),
                defaultDto.movie().getImageUrl(),
                defaultDto.isPicked()
        );
    }
}
