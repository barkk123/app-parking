package com.koma.appparking.services;

import com.koma.appparking.config.ParkingConfigurationProperties;
import com.koma.appparking.domain.ParkingTicket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingTicketFeeService {

    private final ParkingConfigurationProperties parkingConfigurationProperties;

    public BigDecimal calculateTotalFee(ParkingTicket ticket) {
        log.info("Calculating total fee for parking ticket with ID: {}", ticket.getId());

        if (ticket.getArrivalTime() == null || ticket.getDepartureTime() == null) {
            log.error("Arrival and departure times are not set for ticket ID: {}", ticket.getId());
            throw new IllegalArgumentException("Arrival and departure times must be set to calculate the summary.");
        }

        var arrivalTime = ticket.getArrivalTime();
        var departureTime = ticket.getDepartureTime();
        var parkingDuration = Duration.between(arrivalTime, departureTime);

        log.debug("Parking duration for ticket ID {}: {} minutes", ticket.getId(), parkingDuration.toMinutes());

        var hourlyRate = ticket.getParkingSpot().getParking().getHourlyRate();

        if (hourlyRate == null) {
            log.warn("Hourly rate is not defined for the parking spot in ticket ID: {}. Using default hourly rate.", ticket.getId());
            hourlyRate = parkingConfigurationProperties.getHourlyRate();
        }

        var totalFee = BigDecimal.valueOf(Math.ceil(parkingDuration.toMinutes() / 60.0)).multiply(hourlyRate);
        log.info("Total fee for ticket ID {}: {}", ticket.getId(), totalFee);

        return totalFee;
    }
}
