package com.furguard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContactDTO {
    Long emergencyContactId;
    String name;
    String mobileNumber;
}
