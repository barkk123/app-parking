package com.koma.appparking.api;

import com.koma.appparking.domain.ParkingTicket;
import com.koma.appparking.services.ParkingTicketService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking/parking-tickets")
@Validated
class ParkingTicketController {

    @NotNull
    private final ParkingTicketService parkingTicketService;

     ParkingTicketController(ParkingTicketService parkingTicketService) {
        this.parkingTicketService = parkingTicketService;
    }

    @GetMapping
     ResponseEntity<List<ParkingTicket>> getAllTickets() {
        return ResponseEntity.ok(parkingTicketService.getAllTickets());
    }

    @GetMapping("/{id}")
     ResponseEntity<ParkingTicket> getTicketById(@PathVariable Long id) {
        return parkingTicketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
     ResponseEntity<ParkingTicket> createTicket(@RequestBody @Valid ParkingTicketCreateModel model) {
        return ResponseEntity.ok(parkingTicketService.createParkingTicket(model));
    }

    @DeleteMapping("/{id}")
     ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        parkingTicketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

}
