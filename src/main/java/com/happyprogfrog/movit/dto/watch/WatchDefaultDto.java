package com.happyprogfrog.movit.dto.watch;

import com.happyprogfrog.movit.model.Movie;
import com.happyprogfrog.movit.model.User;
import com.happyprogfrog.movit.model.Watch;

import java.time.LocalDateTime;

public record WatchDefaultDto(
    Long watchId,
    User user,
    Movie movie,
    boolean isPicked,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public WatchDefaultDto(Watch watch) {
        this(
                watch.getId(),
                watch.getUser(),
                watch.getMovie(),
                watch.isPicked(),
                watch.getCreatedAt(),
                watch.getUpdatedAt()
        );
    }
}
