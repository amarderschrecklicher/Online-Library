package ba.unsa.etf.membership_service.membership_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

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
