package com.furguard.backend.entities;

import com.furguard.backend.enums.Gender;
import com.furguard.backend.enums.Species;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pet_id;

    @NotNull
    private String name;

    private String image;

    private String description;

    @NotNull
    private Species species;

    @NotNull
    private String breed;

    @NotNull
    @Min(value = 0, message = "Age cannot be less than 0")
    private Integer age;

    @Min(value = 0, message = "Weight cannot be less than 0")
    private String weight;

    private String color;

    @NotNull
    private Gender gender;
}
