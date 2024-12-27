package com.koma.appparking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "parking_ticket")
@Getter
@Setter
public class ParkingTicket {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_spot_id", referencedColumnName = "id", nullable = false)
    private ParkingSpot parkingSpot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_license_number", referencedColumnName = "license_number", nullable = false)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar")
    private TicketStatus status;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    private BigDecimal fee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingTicket that = (ParkingTicket) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(parkingSpot, that.parkingSpot) &&
                status == that.status &&
                Objects.equals(arrivalTime, that.arrivalTime) &&
                Objects.equals(departureTime, that.departureTime) &&
                Objects.equals(fee, that.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
