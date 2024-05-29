package com.happyprogfrog.movit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyprogfrog.movit.dto.watch.request.WatchReqAddDto;
import com.happyprogfrog.movit.dto.watch.request.WatchReqUpdateDto;
import com.happyprogfrog.movit.model.Movie;
import com.happyprogfrog.movit.model.User;
import com.happyprogfrog.movit.model.Watch;
import com.happyprogfrog.movit.repository.MovieRepository;
import com.happyprogfrog.movit.repository.UserRepository;
import com.happyprogfrog.movit.repository.WatchRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WatchControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    WatchRepository watchRepository;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("getAllWatchList: 관람 영화 조회")
    void getAllWatchList() throws Exception {
        // given
        final String url = "/api/v1/watchlist/users/{userId}";
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        Watch watch = watchRepository.save(new Watch(user, movie));

        // when
        final ResultActions result = mockMvc.perform(get(url, watch.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("영화1"));
    }

    @Test
    @DisplayName("addWatch: 관람 영화 추가")
    void addWatch() throws Exception {
        // given
        final String url = "/api/v1/watchlist";
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));

        final WatchReqAddDto reqAddDto = new WatchReqAddDto(user.getId(), movie.getId());
        final String requestBody = objectMapper.writeValueAsString(reqAddDto);

        // when
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Watch> watchList = watchRepository.findAll();

        assertThat(watchList.size()).isEqualTo(1);
        assertThat(watchList.getFirst().getUser().getNickname()).isEqualTo("aaa");
        assertThat(watchList.getFirst().getMovie().getTitle()).isEqualTo("영화1");
    }

    @Test
    @DisplayName("deleteWatch: 관람 영화 삭제")
    void deleteWatch() throws Exception {
        // given
        final String url = "/api/v1/watchlist/{watchId}";
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        Watch watch = watchRepository.save(new Watch(user, movie));

        // when
        ResultActions result = mockMvc.perform(delete(url, watch.getId()));

        // then
        result.andExpect(status().isNoContent());

        List<Watch> watchList = watchRepository.findAll();
        assertThat(watchList).isEmpty();
    }

    @Test
    @DisplayName("getPickedWatchList: 인생 영화 조회")
    void getPickedWatchList() throws Exception {
        // given
        final String url = "/api/v1/watchlist/users/{userId}/pick";
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        Watch watch = watchRepository.save(new Watch(user, movie));
        watch.changeIsPicked(true);
        watchRepository.flush();

        // when
        ResultActions result = mockMvc.perform(get(url, user.getId())
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("영화1"));
    }

    @Test
    @DisplayName("addPick: 인생 영화 추가")
    void addPick() throws Exception {
        // given
        final String url = "/api/v1/watchlist/{watchId}/pick";
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        Watch savedWatch = watchRepository.save(new Watch(user, movie));

        final WatchReqUpdateDto reqUpdateDto = new WatchReqUpdateDto(true);
        final String requestBody = objectMapper.writeValueAsString(reqUpdateDto);

        // when
        final ResultActions result = mockMvc.perform(patch(url, savedWatch.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());

        Watch watch = watchRepository.findById(savedWatch.getId()).get();

        assertThat(watch.getUser().getNickname()).isEqualTo("aaa");
        assertThat(watch.getMovie().getTitle()).isEqualTo("영화1");
        assertThat(watch.isPicked()).isEqualTo(true);
    }

    @Test
    @DisplayName("deletePick: 인생 영화 삭제")
    void deletePick() throws Exception {
        // given
        final String url = "/api/v1/watchlist/{watchId}/pick";
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        Watch savedWatch = watchRepository.save(new Watch(user, movie));

        final WatchReqUpdateDto reqUpdateDto = new WatchReqUpdateDto(false);
        final String requestBody = objectMapper.writeValueAsString(reqUpdateDto);

        // when
        final ResultActions result = mockMvc.perform(patch(url, savedWatch.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());

        Watch watch = watchRepository.findById(savedWatch.getId()).get();

        assertThat(watch.getUser().getNickname()).isEqualTo("aaa");
        assertThat(watch.getMovie().getTitle()).isEqualTo("영화1");
        assertThat(watch.isPicked()).isEqualTo(false);
    }
}