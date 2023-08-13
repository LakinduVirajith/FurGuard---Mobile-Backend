package com.furguard.backend.dto;

import com.furguard.backend.enums.PetSpecies;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LostPetNoticeDTO {
    private Long lostPetNoticeId;
    private String name;
    private String image;
    @Enumerated(EnumType.STRING)
    private PetSpecies species;
    private LocalDate lostDate;
    private String lastSeenLocation;
    private String mobileNumber;
    private Boolean isFound;
}
