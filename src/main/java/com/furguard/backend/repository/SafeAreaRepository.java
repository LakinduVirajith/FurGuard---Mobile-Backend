package com.furguard.backend.repository;

import com.furguard.backend.entity.SafeArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SafeAreaRepository extends JpaRepository<SafeArea, Long> {
}
