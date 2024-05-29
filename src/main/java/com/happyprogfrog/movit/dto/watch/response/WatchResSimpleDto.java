package com.happyprogfrog.movit.dto.watch.response;

import com.happyprogfrog.movit.dto.watch.WatchDefaultDto;

public record WatchResSimpleDto(
        Long movieId,
        String title
) {
    public WatchResSimpleDto(WatchDefaultDto defaultDto) {
        this(
                defaultDto.movie().getId(),
                defaultDto.movie().getTitle()
        );
    }
}
