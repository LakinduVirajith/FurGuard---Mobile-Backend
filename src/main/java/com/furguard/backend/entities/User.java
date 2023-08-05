package com.furguard.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String fullName;

    @NotNull
    private String password;

    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Not a Valid Email")
    private String email;

    @NotNull
    @Pattern(regexp = "^[0-9]*$", message = "Mobile number must contain only numbers")
    @Size(max = 15, message = "Not a valid Mobile")
    private String mobileNumber;

    @NotNull
    private String address;

    @NotNull
    private Boolean isActive = false;


    @Column(length = 64)
    private String activationToken;

    private LocalDateTime activationTokenExpiry;
}
