package com.happyprogfrog.movit.model;

import com.happyprogfrog.movit.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional // 트랜잭션 안에서 테스트를 실행한다
class MovieTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    void checkBook() {
        // 생성 시각 확인
        Movie savedMovie = movieRepository.save(new Movie("제목제목", "감독", "줄거리", "111"));

        Movie createdMovie = movieRepository.findById(savedMovie.getId()).orElseThrow(EntityNotFoundException::new);
        LocalDateTime firstCreatedAt = createdMovie.getCreatedAt();
        LocalDateTime firstUpdatedAt = createdMovie.getUpdatedAt();
        System.out.println("firstCreatedAt: " + firstCreatedAt);
        System.out.println("firstUpdatedAt: " + firstUpdatedAt);

        // 수정 시각 확인
        savedMovie.updateEntity("뉴제목제목", "뉴감독", "뉴줄거리", "222");
        movieRepository.flush(); // 변경 감지 적용 (수정 쿼리 적용)

        Movie updatedMovie = movieRepository.findById(savedMovie.getId()).orElseThrow(EntityNotFoundException::new);
        LocalDateTime secondCreatedAt = updatedMovie.getCreatedAt();
        LocalDateTime secondUpdatedAt = updatedMovie.getUpdatedAt();
        System.out.println("secondCreatedAt: " + secondCreatedAt);
        System.out.println("secondUpdatedAt: " + secondUpdatedAt);

        // 생성 시각은 같고, 수정 시각은 달려졌는지 확인
        assertThat(firstCreatedAt).isEqualTo(secondCreatedAt);
        assertThat(firstUpdatedAt).isNotEqualTo(secondUpdatedAt);
    }
}