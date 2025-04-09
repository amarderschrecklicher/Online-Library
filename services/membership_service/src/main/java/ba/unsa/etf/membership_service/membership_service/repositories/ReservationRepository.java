package ba.unsa.etf.membership_service.membership_service.repositories;

import ba.unsa.etf.membership_service.membership_service.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberId(Long memberId);
}

