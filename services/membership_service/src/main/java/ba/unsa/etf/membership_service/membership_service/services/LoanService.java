package ba.unsa.etf.membership_service.membership_service.services;

import ba.unsa.etf.membership_service.membership_service.dtos.BookDto;
import ba.unsa.etf.membership_service.membership_service.dtos.BookCopyDto;
import ba.unsa.etf.membership_service.membership_service.dtos.LoanDto;
import ba.unsa.etf.membership_service.membership_service.dtos.MemberDto;
import ba.unsa.etf.membership_service.membership_service.mappers.LoanMapper;
import ba.unsa.etf.membership_service.membership_service.mappers.MemberMapper;
import ba.unsa.etf.membership_service.membership_service.models.Loan;
import ba.unsa.etf.membership_service.membership_service.models.Member;
import ba.unsa.etf.membership_service.membership_service.repositories.LoanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LoanService {

    private final LoanRepository loanRepository;
    private final RestTemplate restTemplate;
    private final MemberService memberService;

    @Autowired
    public LoanService(LoanRepository loanRepository, RestTemplate restTemplate, MemberService memberService) {
        this.loanRepository = loanRepository;
        this.restTemplate = restTemplate;
        this.memberService = memberService;
    }

    public List<Loan> getLoans() {return loanRepository.findAll();}

    public Loan getLoanById(Long id) {return loanRepository.getById(id);}

    public Boolean existsByBookCopyId(Long id) {return loanRepository.existsByBookCopyId(id);}

    public boolean existsById(Long id) {return loanRepository.existsById(id);}

    public Loan createLoan(Long memberId, Long bookCopyId, LocalDateTime loanDate, LocalDateTime returnDate, LocalDateTime dueDate) {

        Loan loan = Loan.builder().memberId(memberId)
                .bookCopyId(bookCopyId)
                .loanDate(loanDate)
                .returnDate(returnDate)
                .dueDate(dueDate)
                .returned(false).build();
        Loan savedLoan = loanRepository.save(loan);
        return loan;
    }

    public Map<String, Object> getLoanData(Long longiD) {
        LoanDto loan = LoanMapper.toDto(getLoanById(longiD));


        String url = "http://book-service/api/v1/book-copy/id/" + loan.getBookCopyId();
        BookDto book = restTemplate.getForObject(url,BookDto.class);

        System.out.println(book);
        MemberDto member = MemberMapper.toDto(memberService.getMemberById(loan.getMemberId()));


        Map<String, Object> result = new HashMap<>();
        result.put("loan", loan);
        result.put("book", book);
        result.put("member", member);

        return result;
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
