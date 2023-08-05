package com.furguard.backend.repositories;

import com.furguard.backend.entities.PetProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<PetProfile, Long> {

}
