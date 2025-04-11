package ba.unsa.etf.membership_service.membership_service;

import ba.unsa.etf.membership_service.membership_service.controllers.LoanController;
import ba.unsa.etf.membership_service.membership_service.models.Loan;
import ba.unsa.etf.membership_service.membership_service.repositories.LoanRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanControllerTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanController loanController;

    @Test
    void shouldReturnLoansForMember() {
        Loan loan = new Loan(1L, 100L, 200L, LocalDateTime.now(), null, null, false);
        when(loanRepository.findByMemberId(100L)).thenReturn(List.of(loan));

        List<Loan> result = loanController.getLoansForMember(100L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBookCopyId()).isEqualTo(200L);
    }

    @Test
    void shouldCreateLoan() {
        Loan loan = new Loan(null, 101L, 201L, LocalDateTime.now(), null, null, false);

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        var response = loanController.createLoan(loan);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void shouldReturnBook() {
        Loan loan = new Loan(1L, 100L, 200L, LocalDateTime.now(), null, null, false);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        var response = loanController.returnBook(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(loan.getReturned()).isTrue();
        assertThat(loan.getReturnDate()).isNotNull();
        verify(loanRepository).save(loan);
    }

    @Test
    void shouldReturnNotFoundIfLoanDoesNotExist() {
        when(loanRepository.findById(99L)).thenReturn(Optional.empty());

        var response = loanController.returnBook(99L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        verify(loanRepository, never()).save(any());
    }
}
