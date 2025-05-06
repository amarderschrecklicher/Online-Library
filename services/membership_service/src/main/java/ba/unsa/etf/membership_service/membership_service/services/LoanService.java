package ba.unsa.etf.membership_service.membership_service.services;

import ba.unsa.etf.book_service.book_service.dtos.BookDto;
import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.membership_service.membership_service.dtos.LoanDto;
import ba.unsa.etf.membership_service.membership_service.models.Loan;
import ba.unsa.etf.membership_service.membership_service.models.Member;
import ba.unsa.etf.membership_service.membership_service.repositories.LoanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoanService {
    private final LoanRepository loanRepository;
    private final RestTemplate restTemplate;
    private final MemberService memberService;

    @Value("${book.service.url}")
    private String bookServiceUrl;

    @Autowired
    public LoanService(LoanRepository loanRepository, RestTemplate restTemplate, MemberService memberService) {
        this.loanRepository = loanRepository;
        this.restTemplate = restTemplate;
        this.memberService = memberService;
    }

    public List<Loan> getLoans() {return loanRepository.findAll();}

    public Loan getLoanById(Long id) {return loanRepository.getById(id);}

    public Boolean existsByBookCopyId(Long id) {return loanRepository.existsByBookCopyId(id);}

    public Loan createLoan(Long memberId, Long bookCopyId, LocalDateTime loanDate, LocalDateTime returnDate, LocalDateTime dueDate, Boolean returned) {

        Loan loan = Loan.builder().memberId(memberId)
                .bookCopyId(bookCopyId)
                .loanDate(loanDate)
                .returnDate(returnDate)
                .dueDate(dueDate)
                .returned(returned).build();
        Loan savedLoan = loanRepository.save(loan);
        return loan;
    }

    public Loan CreateLoan(String memberName, String bookTitle, LocalDateTime loanDate, LocalDateTime returnDate, LocalDateTime dueDate, Boolean returned) {
        Optional<Member> memberOpt = memberService.findByUsername(memberName);
        if (memberOpt.isEmpty()) {
            throw new RuntimeException("Member not found: " + memberName);
        }

        Member member = memberOpt.get();


        Book book = restTemplate.getForObject(bookServiceUrl + "/api/v1/book/by-title?title=" + URLEncoder.encode(bookTitle, StandardCharsets.UTF_8), Book.class);



        Loan loan = Loan.builder()
                .memberId(member.getId())
                .bookCopyId(bookDto.getId())
                .loanDate(loanDate)
                .returnDate(returnDate)
                .dueDate(dueDate)
                .returned(returned)
                .build();

        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long id, LoanDto loanDto) {
        Loan existingLoan = loanRepository.getById(id);

        existingLoan.setLoanDate(loanDto.getLoanDate());
        existingLoan.setReturnDate(loanDto.getReturnDate());
        existingLoan.setDueDate(loanDto.getDueDate());
        existingLoan.setReturned(loanDto.getReturned());
        return loanRepository.save(existingLoan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
