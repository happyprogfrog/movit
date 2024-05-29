package com.happyprogfrog.movit.service;

import com.happyprogfrog.movit.dto.watch.WatchDefaultDto;
import com.happyprogfrog.movit.dto.watch.request.WatchReqAddDto;
import com.happyprogfrog.movit.dto.watch.request.WatchReqUpdateDto;
import com.happyprogfrog.movit.model.Movie;
import com.happyprogfrog.movit.model.User;
import com.happyprogfrog.movit.model.Watch;
import com.happyprogfrog.movit.repository.MovieRepository;
import com.happyprogfrog.movit.repository.UserRepository;
import com.happyprogfrog.movit.repository.WatchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class WatchServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    WatchRepository watchRepository;
    @Autowired
    WatchService watchService;

    @Test
    void getAllWatchListByUserId() {
        // given
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        watchRepository.save(new Watch(user, movie));

        // when
        List<WatchDefaultDto> watchList = watchService.getAllWatchListByUserId(user.getId());

        // then
        assertThat(watchList.size()).isEqualTo(1);
    }

    @Test
    void addWatch() {
        // given
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));

        // when
        WatchReqAddDto reqAddDto = new WatchReqAddDto(user.getId(), movie.getId());
        WatchDefaultDto defaultDto = watchService.addWatch(reqAddDto);
        Watch watch = watchRepository.findById(defaultDto.watchId()).orElseThrow(EntityNotFoundException::new);

        // then
        assertThat(watch.getUser().getEmail()).isEqualTo("a@qmail.com");
        assertThat(watch.getMovie().getTitle()).isEqualTo("영화1");
    }

    @Test
    void deleteWatch() {
        // given
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        Watch watch = watchRepository.save(new Watch(user, movie));

        // when
        watchService.deleteWatch(watch.getId());

        // then
        List<Watch> watchList = watchRepository.findAll();
        assertThat(watchList.size()).isEqualTo(0);
    }

    @Test
    void getPickedWatchList() {
        // given
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        Watch watch = watchRepository.save(new Watch(user, movie));
        watch.changeIsPicked(true);
        watchRepository.flush();

        // when
        List<WatchDefaultDto> pickedWatchList = watchService.getPickedWatchList();

        // then
        assertThat(pickedWatchList.size()).isEqualTo(1);
    }

    @Test
    void changePickStatus() {
        // given
        User user =  userRepository.save(new User("a@qmail.com", "aaa", "1234"));
        Movie movie =  movieRepository.save(new Movie("영화1", "AAA", "솰라솰라", "111"));
        Watch watch = watchRepository.save(new Watch(user, movie));
        WatchReqUpdateDto reqUpdateDto = new WatchReqUpdateDto(true);

        // when
        watchService.changePickStatus(watch.getId(), reqUpdateDto);

        // then
        Watch find = watchRepository.findById(watch.getId()).orElseThrow(EntityNotFoundException::new);
        assertThat(find.isPicked()).isEqualTo(true);
    }
}