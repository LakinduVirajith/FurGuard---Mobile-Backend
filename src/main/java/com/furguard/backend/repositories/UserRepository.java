package com.furguard.backend.repositories;

import com.furguard.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByActivationToken(String activationToken);

    User findByEmailIgnoreCase(String email);
}
