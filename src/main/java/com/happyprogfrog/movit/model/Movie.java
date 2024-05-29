package com.happyprogfrog.movit.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Movie extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String director;

    private String summary;

    @Column(name = "image_url")
    private String imageUrl;

    protected Movie() {}

    public Movie(String title, String director, String summary, String imageUrl) {
        this.title = title;
        this.director = director;
        this.summary = summary;
        this.imageUrl = imageUrl;
    }

    public void updateEntity(String title, String director, String summary, String imageUrl) {
        this.title = title;
        this.director = director;
        this.summary = summary;
        this.imageUrl = imageUrl;
    }
}
