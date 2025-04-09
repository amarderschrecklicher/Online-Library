package ba.unsa.etf.book_service.book_service.dtos;

import lombok.Data;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class BookDto {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @Size(min = 3, max = 50, message = "Genre must be between 3 and 50 characters")
    private String genre;
    @NotNull(message = "Published year is required")
    private Long publishedYear;
    private List<BookCopyDto> bookCopies;
}
