package ba.unsa.etf.membership_service.membership_service.services;

import ba.unsa.etf.membership_service.membership_service.models.Loan;
import ba.unsa.etf.membership_service.membership_service.repositories.LoanRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipStatisticsService {

    private final LoanRepository loanRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MembershipStatisticsService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void checkNPlusOneProblemForLoans(Long memberId) {
        int before = getQueryExecutionCount();

        List<Loan> loans = loanRepository.findByMemberId(memberId);
        for (Loan loan : loans) {
            System.out.println("Member username: " + loan.getMember().getUsername());
        }

        int after = getQueryExecutionCount();
        int executedQueries = after - before;

        System.out.println("Executed queries for Loans: " + executedQueries);

        if (executedQueries > 1) {
            System.out.println("❌ N+1 problem detected!");
        } else {
            System.out.println("✅ No N+1 problem detected.");
        }
    }

    private int getQueryExecutionCount() {
        Session session = entityManager.unwrap(Session.class);
        return (int) session.getSessionFactory().getStatistics().getQueryExecutionCount();
    }
}
