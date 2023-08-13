package com.furguard.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostPetNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lostPetNoticeId;

    @OneToOne
    @JoinColumn(name = "lpn_pet_id")
    private PetProfile petProfile;

    @NotNull
    private LocalDate lostDate;

    @NotNull
    private String lastSeenLocation;

    @NotNull
    @Pattern(regexp = "^[0-9]*$", message = "Mobile number must contain only numbers")
    @Size(max = 15, message = "Not a valid Mobile")
    private String mobileNumber;

    private String additionalDetails;

    private LocalDate foundDate;

    private Boolean isFound = false;
}
