package com.furguard.backend.dto;

import com.furguard.backend.enums.PetGender;
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
public class LostPetNoticeDetailsDTO {
    private Long lostPetNoticeId;
    private String name;
    private String image;
    @Enumerated(EnumType.STRING)
    private PetSpecies species;
    private String breed;
    private Integer age;
    private PetGender gender;
    private LocalDate lostDate;
    private String lastSeenLocation;
    private String mobileNumber;
    private String additionalDetails;
    private LocalDate foundDate;
    private Boolean isFound;

}
