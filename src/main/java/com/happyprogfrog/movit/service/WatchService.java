package com.happyprogfrog.movit.service;

import com.happyprogfrog.movit.dto.watch.WatchDefaultDto;
import com.happyprogfrog.movit.dto.watch.request.WatchReqAddDto;
import com.happyprogfrog.movit.dto.watch.request.WatchReqUpdateDto;
import com.happyprogfrog.movit.exception.ResourceNotFoundException;
import com.happyprogfrog.movit.model.Movie;
import com.happyprogfrog.movit.model.User;
import com.happyprogfrog.movit.model.Watch;
import com.happyprogfrog.movit.repository.MovieRepository;
import com.happyprogfrog.movit.repository.UserRepository;
import com.happyprogfrog.movit.repository.WatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WatchService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final WatchRepository watchRepository;

    private static final int LIMIT_PICKED = 5;

    // 관람 영화 조회
    @Transactional(readOnly = true)
    public List<WatchDefaultDto> getAllWatchListByUserId(Long userId) {
        return watchRepository.findByUserId(userId)
                .stream()
                .map(WatchDefaultDto::new)
                .toList();
    }

    // 관람 영화 추가
    public WatchDefaultDto addWatch(WatchReqAddDto reqAddDto) {
        User user = findUserById(reqAddDto.userId());
        Movie movie = findMovieById(reqAddDto.movieId());
        Watch watch = watchRepository.save(new Watch(user, movie));
        return new WatchDefaultDto(watch);
    }

    // 관람 영화 삭제
    public void deleteWatch(Long watchId) {
        Watch watch = findWatchById(watchId);
        watchRepository.delete(watch);
    }

    // 인생 영화 조회
    public List<WatchDefaultDto> getPickedWatchList() {
        Page<Watch> page = watchRepository.findByIsPickedTrue(PageRequest.of(0, LIMIT_PICKED));
        return page.getContent()
                .stream()
                .map(WatchDefaultDto::new)
                .toList();
    }

    // 인생 영화 추가 또는 삭제
    public WatchDefaultDto changePickStatus(Long watchId, WatchReqUpdateDto reqUpdateDto) {
        Watch watch = findWatchById(watchId);
        watch.changeIsPicked(reqUpdateDto.isPicked());
        return new WatchDefaultDto(watch);
    }

    @Transactional(readOnly = true)
    private Watch findWatchById(Long watchId) {
        return watchRepository.findById(watchId)
                .orElseThrow(() -> new ResourceNotFoundException("Watch", "id", watchId));
    }

    @Transactional(readOnly = true)
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Transactional(readOnly = true)
    private Movie findMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));
    }
}
