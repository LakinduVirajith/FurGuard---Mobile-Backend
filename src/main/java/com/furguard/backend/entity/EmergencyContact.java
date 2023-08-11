package com.furguard.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emergencyContactId;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^[0-9]*$", message = "Mobile number must contain only numbers")
    @Size(max = 15, message = "Not a valid Mobile")
    private String mobileNumber;

    @ManyToOne
    @JoinColumn(name = "e_user_id")
    private User user;
}
