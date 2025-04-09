package ba.unsa.etf.membership_service.membership_service.controllers;

import ba.unsa.etf.membership_service.membership_service.models.Loan;
import ba.unsa.etf.membership_service.membership_service.repositories.LoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/loans")
@CrossOrigin
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/member/{memberId}")
    public List<Loan> getLoansForMember(@PathVariable Long memberId) {
        return loanRepository.findByMemberId(memberId);
    }

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanRepository.save(loan));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        Optional<Loan> loan = loanRepository.findById(id);
        if (loan.isEmpty()) return ResponseEntity.notFound().build();

        loan.get().setReturned(true);
        loan.get().setReturnDate(LocalDateTime.now());
        return ResponseEntity.ok(loanRepository.save(loan.get()));
    }
}
