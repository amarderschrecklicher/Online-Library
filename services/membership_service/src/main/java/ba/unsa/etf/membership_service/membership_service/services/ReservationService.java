package ba.unsa.etf.membership_service.membership_service.services;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ba.unsa.etf.membership_service.membership_service.config.RabbitConfig;
import ba.unsa.etf.membership_service.membership_service.dtos.BookReservedEvent;
import ba.unsa.etf.membership_service.membership_service.dtos.MembershipReservationConfirmedEvent;
import ba.unsa.etf.membership_service.membership_service.dtos.MembershipReservationFailedEvent;
import ba.unsa.etf.membership_service.membership_service.models.Reservation;
import ba.unsa.etf.membership_service.membership_service.repositories.ReservationRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RabbitTemplate rabbitTemplate) {
        this.reservationRepository = reservationRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    @RabbitListener(queues = RabbitConfig.MEMBERSHIP_QUEUE)
    public void handleBookReserved(BookReservedEvent bookReservedEvent) throws JsonProcessingException{

        boolean allowed = checkEligibility(bookReservedEvent.getMemberId());

        if (allowed){
            Reservation reservation = new Reservation();
            reservation.setBookCopyId(bookReservedEvent.getBookId());
            reservation.setMemberId(bookReservedEvent.getMemberId());
            reservation.setReservationDate(LocalDateTime.now());
            reservation.setStatus("RESERVED");
            reservationRepository.save(reservation);

            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, "book.membership.confirmed",
                    new MembershipReservationConfirmedEvent(
                            bookReservedEvent.getReservationId(), bookReservedEvent.getBookId(), bookReservedEvent.getMemberId()));
            
        }
        else {
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE,"book.membership.failed",
                    new MembershipReservationFailedEvent(
                            bookReservedEvent.getReservationId(), bookReservedEvent.getBookId(), bookReservedEvent.getMemberId()));

        }
    }
    
    public boolean checkEligibility(Long memberId) {
    
        return !memberId.equals(Long.valueOf(2));
    }

    public void cancelMembershipReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
