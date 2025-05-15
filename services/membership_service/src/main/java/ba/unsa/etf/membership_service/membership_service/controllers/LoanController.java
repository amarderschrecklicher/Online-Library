package ba.unsa.etf.membership_service.membership_service.controllers;

import ba.unsa.etf.membership_service.membership_service.dtos.LoanDto;
import ba.unsa.etf.membership_service.membership_service.models.Loan;

import ba.unsa.etf.membership_service.membership_service.services.LoanService;
import jakarta.validation.Valid;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loans")
@CrossOrigin
public class LoanController {

    @Autowired
    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getLoans();
    }

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody LoanDto loanDto) {
        if(loanService.existsByBookCopyId(loanDto.getBookCopyId())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Loan already exists");
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(error);
        }

        Loan newLoan = loanService.createLoan(
                loanDto.getMemberId(),
                loanDto.getBookCopyId(),
                loanDto.getLoanDate(),
                loanDto.getReturnDate(),
                loanDto.getDueDate(),
                loanDto.getReturned()
        );
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(newLoan);
    }

    @PutMapping(path = "/loanId")
    public ResponseEntity<?> updateLoan(@PathVariable("loanId") Long id, @Valid @RequestBody LoanDto updatedLoanData){
        if(loanService.existsByBookCopyId(updatedLoanData.getBookCopyId())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Loan already exists");
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(error);
        }
        try{
            Loan updatedLoan = loanService.updateLoan(id, updatedLoanData);

            if(updatedLoan == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedLoan);
        }catch (Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @DeleteMapping(path="loanId")
    public ResponseEntity<?> deleteLoan(@PathVariable("loanId") Long id){
        System.out.println("Delete called!");
        try{
            loanService.deleteLoan(id);
            Map<String,String> response = new HashMap<>();
            response.put("message","Loan deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete book.");
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(error);
        }

    }


}
