package com.koma.appparking.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ParkingTicketPayModel(
        @NotBlank(message = "Vehicle registration number cannot be empty or contain only whitespace")
        @Pattern(regexp = "^[A-Z0-9]+$", message = "Vehicle registration number must be alphanumeric")
        String vehicleLicenseNumber) {
}