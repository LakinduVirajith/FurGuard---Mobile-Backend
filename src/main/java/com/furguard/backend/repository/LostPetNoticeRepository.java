package com.furguard.backend.repository;

import com.furguard.backend.entity.LostPetNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LostPetNoticeRepository extends JpaRepository<LostPetNotice, Long> {
    List<LostPetNotice> findByIsFoundAndFoundDateBefore(boolean b, LocalDate sevenDaysAgo);
}
