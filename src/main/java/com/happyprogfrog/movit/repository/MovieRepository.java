package com.happyprogfrog.movit.repository;

import com.happyprogfrog.movit.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
