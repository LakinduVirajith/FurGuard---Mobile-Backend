package com.furguard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SafeAreaDTO {
    private double centerLatitude;
    private double centerLongitude;
    private double radius;
}
