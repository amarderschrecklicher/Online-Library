package ba.unsa.etf.membership_service.membership_service.mappers;

import ba.unsa.etf.membership_service.membership_service.dtos.LoanDto;
import ba.unsa.etf.membership_service.membership_service.models.Loan;

public class LoanMapper {
    public static LoanDto toDto(Loan loan) {
        if (loan == null) return null;

        LoanDto dto = new LoanDto();
        dto.setId(loan.getId());
        dto.setMemberId(loan.getMemberId());
        dto.setBookCopyId(loan.getBookCopyId());
        dto.setLoanDate(loan.getLoanDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturned(loan.getReturned());
        return dto;
    }
}
