package com.koma.appparking.repository;

import com.koma.appparking.domain.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
    @Query("select pt from ParkingTicket pt " +
            "where pt.id = :id")
    ParkingTicket findByTicketId(Long id);

}
