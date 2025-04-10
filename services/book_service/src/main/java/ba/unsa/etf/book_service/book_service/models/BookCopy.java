package ba.unsa.etf.book_service.book_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name="book_copies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy {
    @Id
    @SequenceGenerator(
            name = "book_copy_sequence",
            sequenceName = "book_copy_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_copy_sequence"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;
    @Column(unique = true, nullable = false) 
    private String code;
    private String status;
    
}
