package com.happyprogfrog.movit.service;

import com.happyprogfrog.movit.dto.movie.request.MovieReqAddDto;
import com.happyprogfrog.movit.dto.movie.MovieDefaultDto;
import com.happyprogfrog.movit.dto.movie.request.MovieReqUpdateDto;
import com.happyprogfrog.movit.exception.ResourceNotFoundException;
import com.happyprogfrog.movit.model.Movie;
import com.happyprogfrog.movit.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieServiceTest {

    @Autowired MovieService movieService;
    @Autowired MovieRepository movieRepository;

    @AfterEach
    public void cleanUp() {
        movieRepository.deleteAll();
    }

    @Test
    void getAllMovies() {
        // given
        Movie movie1 = new Movie("영화1", "AAA", "솰라솰라", "111");
        Movie movie2 = new Movie("영화2", "BBB", "어쩌구저쩌구", "222");
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        // when
        List<MovieDefaultDto> movies = movieService.getAllMovies();

        // then
        assertThat(movies.size()).isEqualTo(2);
    }

    @Test
    void getMovieById() {
        // given
        Movie movie = new Movie("영화1", "AAA", "솰라솰라", "111");
        Movie savedMovie = movieRepository.save(movie);

        // when
        MovieDefaultDto defaultDto = movieService.getMovieById(savedMovie.getId());

        // then
        assertThat(defaultDto.movieId()).isEqualTo(savedMovie.getId());
        assertThat(defaultDto.title()).isEqualTo("영화1");
        assertThat(defaultDto.director()).isEqualTo("AAA");
        assertThat(defaultDto.summary()).isEqualTo("솰라솰라");
        assertThat(defaultDto.imageUrl()).isEqualTo("111");
    }

    @Test
    @DisplayName("존재하지 않는 영화 id로 찾으려고 하면 예외 발생")
    void getMovieByIdException() {
        Long movieId = 1L;

        assertThrows(
                ResourceNotFoundException.class,
                () -> movieService.getMovieById(movieId)
        );
    }

    @Test
    void addMovie() {
        // given
        MovieReqAddDto reqAddDto = new MovieReqAddDto("영화A", "감독A", "어쩌구", "222");

        // when
        movieService.addMovie(reqAddDto);

        // then
        assertThat(movieRepository.count()).isEqualTo(1);
    }

    @Test
    void updateMovie() {
        // given
        Movie savedMovie = movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        MovieReqUpdateDto reqUpdateDto = new MovieReqUpdateDto("영화B", "감독B", "저쩌구", "222");

        // when
        movieService.updateMovie(savedMovie.getId(), reqUpdateDto);
        Movie movie = movieRepository.findById(savedMovie.getId()).orElseThrow(EntityNotFoundException::new);

        // then
        assertThat(movie.getId()).isEqualTo(savedMovie.getId());
        assertThat(movie.getTitle()).isEqualTo("영화B");
        assertThat(movie.getDirector()).isEqualTo("감독B");
        assertThat(movie.getSummary()).isEqualTo("저쩌구");
        assertThat(movie.getImageUrl()).isEqualTo("222");
    }

    @Test
    void deleteMovie() {
        // given
        Movie movie1 = new Movie("영화1", "AAA", "솰라솰라", "111");
        Movie movie2 = new Movie("영화2", "BBB", "어쩌구저쩌구", "222");
        Movie savedMovie1 = movieRepository.save(movie1);
        movieRepository.save(movie2);

        // when
        movieService.deleteMovie(savedMovie1.getId());

        // then
        assertThat(movieRepository.count()).isEqualTo(1);
    }
}