package ba.unsa.etf.laon_reservation_service.loan_reservation_service;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @SequenceGenerator(
            name = "loan_sequence",
            sequenceName = "loan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "loan_sequence"
    )
    private Long id;
    private Long bookCopyId;
    private Long memberId;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;   
    private Boolean returned;
    @OneToMany
    private List<Reservation> reservations;
}
