package com.koma.appparking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "parking_ticket")
@Getter
@Setter
public class ParkingTicket {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parking_spot_id", nullable = false)
    private ParkingSpot parkingSpot;

    @ManyToOne
    @JoinColumn(name = "license_number", nullable = false)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private TicketStatus status; // Możliwe wartości: WOLNE, ZAJĘTE

    private LocalDateTime arriveTime;

    private LocalDateTime departureTime;

    private BigDecimal fee;
// metody equal i hashcode w każdej encji
}

