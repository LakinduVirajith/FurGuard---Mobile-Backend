package com.furguard.backend.repository;

import com.furguard.backend.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    List<Allergy> findAllByPetProfilePetId(Long petId);
}
