package com.furguard.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotNull
    private String fullName;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    @Pattern(regexp = "^[0-9]*$", message = "Mobile number must contain only numbers")
    @Size(max = 15, message = "Not a valid Mobile")
    private String mobileNumber;
}
