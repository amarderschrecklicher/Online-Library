package ba.unsa.etf.book_service.book_service.dtos;

import lombok.Data;

@Data
public class BookCopyDto {
    private Long id;
    private Long number;
    private String status;
    private Long bookId; // Optional: if you need to know which book this copy belongs to
}

