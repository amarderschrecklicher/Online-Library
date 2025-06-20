package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;

@Data
public class MembershipReservationFailedEvent {
    public MembershipReservationFailedEvent(String reservationId2, Long bookId2, Long memberId2) {
        this.reservationId = reservationId2;
        this.bookId = bookId2;
        this.memberId = memberId2;
    }

    private String reservationId;
    private Long bookId;
    private Long memberId;

    private final String type = "MembershipReservationFailedEvent";

}
