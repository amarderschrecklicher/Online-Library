package ba.unsa.etf.book_service.book_service.dtos;

import lombok.Data;

@Data
public class MembershipReservationConfirmedEvent {
    public MembershipReservationConfirmedEvent(String reservationId2, Long bookId2, Long memberId2) {
        this.reservationId = reservationId2;
        this.bookId = bookId2;
        this.memberId = memberId2;
    }
    private String reservationId;
    private Long bookId;
    private Long memberId;
}
