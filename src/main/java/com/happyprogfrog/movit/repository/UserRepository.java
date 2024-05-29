package com.happyprogfrog.movit.repository;

import com.happyprogfrog.movit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
