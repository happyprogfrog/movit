package com.happyprogfrog.movit.service;

import com.happyprogfrog.movit.dto.movie.request.MovieReqAddDto;
import com.happyprogfrog.movit.dto.movie.MovieDefaultDto;
import com.happyprogfrog.movit.dto.movie.request.MovieReqUpdateDto;
import com.happyprogfrog.movit.exception.ResourceNotFoundException;
import com.happyprogfrog.movit.model.Movie;
import com.happyprogfrog.movit.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    /**
     * 모든 영화 조회
     */
    @Transactional(readOnly = true)
    public List<MovieDefaultDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(MovieDefaultDto::new)
                .toList();
    }

    /**
     * 영화 id로 조회
     */
    @Transactional(readOnly = true)
    public MovieDefaultDto getMovieById(Long movieId) {
        Movie movie = findMovieById(movieId);
        return new MovieDefaultDto(movie);
    }

    /**
     * 영화 추가
     */
    public MovieDefaultDto addMovie(MovieReqAddDto reqAddDto) {
        Movie movie = movieRepository.save(reqAddDto.toEntity());
        return new MovieDefaultDto(movie);
    }

    /**
     * 영화 수정
     */
    public MovieDefaultDto updateMovie(Long movieId, MovieReqUpdateDto reqUpdateDto) {
        Movie movie = findMovieById(movieId);
        movie.updateEntity(reqUpdateDto.title(), reqUpdateDto.director(), reqUpdateDto.summary(), reqUpdateDto.imageUrl());
        return new MovieDefaultDto(movie);
    }

    /**
     * 영화 삭제
     */
    public void deleteMovie(Long movieId) {
        Movie movie = findMovieById(movieId);
        movieRepository.delete(movie);
    }

    /**
     * 특정 id를 가진 영화 찾기
     */
    @Transactional(readOnly = true)
    private Movie findMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));
    }
}
