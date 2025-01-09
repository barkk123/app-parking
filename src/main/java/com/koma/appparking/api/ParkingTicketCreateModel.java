package com.koma.appparking.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ParkingTicketCreateModel(

        @NotBlank(message = "Vehicle registration number cannot be null or empty")
        @Pattern(regexp = "^[A-Z0-9]+$", message = "Vehicle registration number must be alphanumeric")
        String vehicleRegistrationNumber
){

}

