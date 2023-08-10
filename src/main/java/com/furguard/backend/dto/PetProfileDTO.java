package com.furguard.backend.dto;

import com.furguard.backend.enums.PetGender;
import com.furguard.backend.enums.PetSpecies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetProfileDTO {
    String name;
    String image;
    String description;
    PetSpecies species;
    String breed;
    Integer age;
    String weight;
    String color;
    PetGender gender;
}
