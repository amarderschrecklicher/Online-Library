package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;

@Data
public class BookReservedEvent {

    private Long bookId;
    private Long memberId;
    private String reservationId;    
    
    public BookReservedEvent(Long bookId2, Long memberId2, String string) {
        this.bookId = bookId2;
        this.memberId = memberId2;
        this.reservationId = string;
    }
}
