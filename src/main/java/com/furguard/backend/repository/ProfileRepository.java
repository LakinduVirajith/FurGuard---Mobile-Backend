package com.furguard.backend.repository;

import com.furguard.backend.entity.PetProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<PetProfile, Long> {
    Optional<PetProfile> findByUserUserId(Long userId);

    List<PetProfile> findByDeactivatedDateBefore(LocalDate sixMonthAgo);
}
