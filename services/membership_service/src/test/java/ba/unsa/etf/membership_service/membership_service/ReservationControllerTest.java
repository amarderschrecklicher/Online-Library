package ba.unsa.etf.membership_service.membership_service;

import ba.unsa.etf.membership_service.membership_service.controllers.ReservationController;
import ba.unsa.etf.membership_service.membership_service.models.Reservation;
import ba.unsa.etf.membership_service.membership_service.repositories.ReservationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    void shouldReturnReservationsForMember() {
        Reservation res = new Reservation(1L, 10L, 200L, LocalDateTime.now(), "PENDING");

        when(reservationRepository.findByMemberId(10L)).thenReturn(List.of(res));

        List<Reservation> result = reservationController.getReservationsForMember(10L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBookCopyId()).isEqualTo(200L);
    }

    @Test
    void shouldCreateReservation() {
        Reservation res = new Reservation(null, 11L, 300L, null, "NEW");

        when(reservationRepository.save(any(Reservation.class))).thenReturn(res);

        var response = reservationController.reserveBook(res);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(reservationRepository).save(any(Reservation.class));
    }
}
