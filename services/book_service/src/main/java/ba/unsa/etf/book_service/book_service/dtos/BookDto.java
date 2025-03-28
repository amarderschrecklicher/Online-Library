package ba.unsa.etf.book_service.book_service.dtos;

import lombok.Data;
import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Long publishedYear;
    private List<BookCopyDto> bookCopies;
}
