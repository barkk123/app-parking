package com.koma.appparking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parking_ticket")
@Getter
@Setter
@Builder
public class ParkingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotNull
    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    private BigDecimal fee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (ParkingTicket) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}