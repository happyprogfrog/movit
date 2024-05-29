package com.happyprogfrog.movit.repository;

import com.happyprogfrog.movit.model.Watch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchRepository extends JpaRepository<Watch, Long> {
    List<Watch> findByUserId(Long userId);

    Page<Watch> findByIsPickedTrue(Pageable pageable);
}
