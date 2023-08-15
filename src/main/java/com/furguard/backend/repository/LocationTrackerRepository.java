package com.furguard.backend.repository;

import com.furguard.backend.entity.LocationTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationTrackerRepository extends JpaRepository<LocationTracker, Long> {
}
