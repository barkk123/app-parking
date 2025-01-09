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
        if (ticket.getArrivalTime() == null || ticket.getDepartureTime() == null) {
            throw new IllegalArgumentException("Arrival and departure times must be set to calculate the summary.");
        }

        var arrivalTime = ticket.getArrivalTime();
        var departureTime = ticket.getDepartureTime();
        var parkingDuration = Duration.between(arrivalTime, departureTime);

        var hourlyRate = ticket.getParkingSpot().getParking().getHourlyRate();

        if (hourlyRate == null) {
            return BigDecimal.valueOf(Math.ceil(parkingDuration.toMinutes() / 60.0)).multiply(parkingConfigurationProperties.getHourlyRate());
        }

        return BigDecimal.valueOf(Math.ceil(parkingDuration.toMinutes() / 60.0)).multiply(hourlyRate);
    }

}
