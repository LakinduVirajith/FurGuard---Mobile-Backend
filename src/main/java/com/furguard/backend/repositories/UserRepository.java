package com.furguard.backend.repositories;

import com.furguard.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByActivationToken(String activationToken);

    Optional<User> findByEmail(String email);
}
