package com.happyprogfrog.movit.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Watch extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watch_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false, updatable = false)
    private Movie movie;

    @Column(name = "is_picked", nullable = false)
    private boolean isPicked;

    protected Watch() {}

    public Watch(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
        this.isPicked = false;
    }

    public void changeIsPicked(boolean isPicked) {
        this.isPicked = isPicked;
    }
}
