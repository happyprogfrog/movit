package com.happyprogfrog.movit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyprogfrog.movit.dto.movie.request.MovieReqAddDto;
import com.happyprogfrog.movit.dto.movie.request.MovieReqUpdateDto;
import com.happyprogfrog.movit.model.Movie;
import com.happyprogfrog.movit.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void cleanUp() {
        movieRepository.deleteAll();
    }

    @Test
    @DisplayName("getAllMovies: 모든 영화를 조회하기 성공")
    void getAllMovies() throws Exception {
        // given
        final String url = "/api/v1/movies";
        final String title = "제목제목";
        final String imageUrl = "111";

        movieRepository.save(new Movie(title, null, null, imageUrl));

        // when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].imageUrl").value(imageUrl));
    }

    @Test
    @DisplayName("getMovieById: 영화 ID로 조회 성공")
    void getMovieById() throws Exception {
        // given
        final String url = "/api/v1/movies/{movieId}";
        final String title = "제목제목";
        final String summary = "요약요약";

        final Movie savedMovie = movieRepository.save(new Movie(title, null, summary, null));

        // when
        final ResultActions result = mockMvc.perform(get(url, savedMovie.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.summary").value(summary));
    }

    @Test
    @DisplayName("addMovie: 영화 추가 성공")
    void addMovie() throws Exception {
        // given
        final String url = "/api/v1/movies";
        final String title = "제목제목";
        final String summary = "요약요약";

        final MovieReqAddDto reqAddDto = new MovieReqAddDto(title, null, summary, null);
        final String requestBody = objectMapper.writeValueAsString(reqAddDto);

        // when
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Movie> movies = movieRepository.findAll();

        assertThat(movies.size()).isEqualTo(1);
        assertThat(movies.getFirst().getTitle()).isEqualTo(title);
        assertThat(movies.getFirst().getSummary()).isEqualTo(summary);
    }

    @Test
    @DisplayName("updateMovie: 영화 수정 성공")
    void updateMovie() throws Exception {
        // given
        final String url = "/api/v1/movies/{movieId}";
        final String title = "제목제목";

        final Movie savedMovie = movieRepository.save(new Movie(title, null, null, null));

        final String newTitle = "새로운제목";
        final String newDirector = "새로운감독";

        final MovieReqUpdateDto reqUpdateDto = new MovieReqUpdateDto(newTitle, newDirector, null, null);
        final String requestBody = objectMapper.writeValueAsString(reqUpdateDto);

        // when
        final ResultActions result = mockMvc.perform(patch(url, savedMovie.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());

        Movie movie = movieRepository.findById(savedMovie.getId()).get();

        assertThat(movie.getTitle()).isEqualTo(newTitle);
        assertThat(movie.getDirector()).isEqualTo(newDirector);
    }

    @Test
    @DisplayName("deleteMovie: 영화 삭제 성공")
    void deleteMovie() throws Exception {
        // given
        final String url = "/api/v1/movies/{movieId}";
        final String title = "제목제목";

        final Movie savedMovie = movieRepository.save(new Movie(title, null, null, null));

        // when
        ResultActions result = mockMvc.perform(delete(url, savedMovie.getId()));

        // then
        result.andExpect(status().isNoContent());

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies).isEmpty();
    }
}