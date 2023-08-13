package com.furguard.backend.repository;

import com.furguard.backend.entity.Medication;
import com.furguard.backend.entity.MedicationReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationReminderRepository extends JpaRepository<MedicationReminder, Long> {
    List<MedicationReminder> findByMedicationMedicationId(Long medicationId);
}
