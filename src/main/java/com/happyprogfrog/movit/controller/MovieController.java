package com.happyprogfrog.movit.controller;

import com.happyprogfrog.movit.dto.movie.request.MovieReqAddDto;
import com.happyprogfrog.movit.dto.movie.request.MovieReqUpdateDto;
import com.happyprogfrog.movit.dto.movie.response.MovieResDetailDto;
import com.happyprogfrog.movit.dto.movie.response.MovieResSimpleDto;
import com.happyprogfrog.movit.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    /**
     * 모든 영화 조회
     */
    @GetMapping
    public ResponseEntity<List<MovieResSimpleDto>> getAllMovies() {
        List<MovieResSimpleDto> movies = movieService.getAllMovies()
                .stream()
                .map(MovieResSimpleDto::new)
                .toList();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    /**
     * 영화 ID로 조회
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResDetailDto> getMovieById(@PathVariable("movieId") Long movieId) {
        MovieResDetailDto resDetailDto = new MovieResDetailDto(movieService.getMovieById(movieId));
        return new ResponseEntity<>(resDetailDto, HttpStatus.OK);
    }

    /**
     * 영화 추가
     */
    @PostMapping
    public ResponseEntity<MovieResSimpleDto> addMovie(@Valid @RequestBody MovieReqAddDto reqAddDto) {
        MovieResSimpleDto resSimpleDto = new MovieResSimpleDto(movieService.addMovie(reqAddDto));
        return new ResponseEntity<>(resSimpleDto, HttpStatus.CREATED);
    }

    /**
     * 영화 수정
     */
    @PatchMapping("/{movieId}")
    public ResponseEntity<MovieResSimpleDto> updateMovie(@PathVariable("movieId") Long movieId, @Valid @RequestBody MovieReqUpdateDto reqUpdateDto) {
        MovieResSimpleDto resSimpleDto = new MovieResSimpleDto(movieService.updateMovie(movieId, reqUpdateDto));
        return new ResponseEntity<>(resSimpleDto, HttpStatus.OK);
    }

    /**
     * 영화 삭제
     */
    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("movieId") Long movieId) {
        movieService.deleteMovie(movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
