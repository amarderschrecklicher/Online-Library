package ba.unsa.etf.membership_service.membership_service.repositories;

import ba.unsa.etf.membership_service.membership_service.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByMemberId(Long memberId);
    Boolean existsByBookCopyId(Long bookCopyId);
    boolean existsById(Long id);
}
