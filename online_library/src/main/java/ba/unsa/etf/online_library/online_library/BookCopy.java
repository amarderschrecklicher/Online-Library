package ba.unsa.etf.online_library.online_library;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="book_copies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy extends Book {
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
    private Long number;
    private String status;
    
}
