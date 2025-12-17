package com.trone.poi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PoiRequest {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull(message = "Latitude is required.")
    private Double latitude;

    @NotNull(message = "Longitude is required.")
    private Double longitude;

    @NotNull(message = "Construction year is required.")
    @Min(value = 1, message = "Construction year must be a > 0.")
    private int constructionYear;
}
