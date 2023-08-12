package com.furguard.backend.repository;

import com.furguard.backend.entity.VaccinationReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationReminderRepository extends JpaRepository<VaccinationReminder, Long> {
    List<VaccinationReminder> findByVaccinationVaccinationId(Long vaccinationId);
}
