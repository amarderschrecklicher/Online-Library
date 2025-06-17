package ba.unsa.etf.membership_service.membership_service.controllers;

import ba.unsa.etf.membership_service.membership_service.models.Reservation;
import ba.unsa.etf.membership_service.membership_service.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationRepository reservationService;

    @GetMapping("/member/{memberId}")
    public List<Reservation> getReservationsForMember(@PathVariable Long memberId) {
        return reservationService.findByMemberId(memberId);
    }

    @PostMapping
    public ResponseEntity<?> reserveBook(@RequestBody Reservation reservation) {
        reservation.setReservationDate(LocalDateTime.now());
        return ResponseEntity.ok(reservationService.save(reservation));
    }
}
