package ba.unsa.etf.book_service.book_service.mappers;

import ba.unsa.etf.book_service.book_service.dtos.BookCopyDto;
import ba.unsa.etf.book_service.book_service.models.BookCopy;

import java.util.ArrayList;
import java.util.List;

public class BookCopyMapper {

    public static BookCopyDto toDto(BookCopy copy) {
        BookCopyDto dto = new BookCopyDto();
        dto.setId(copy.getId());
        dto.setCode(copy.getCode());
        dto.setAvailable(copy.getAvailable());
        dto.setBookId(copy.getBook().getId());
        return dto;
    }

    public static List<BookCopyDto> toDto(List<BookCopy> bookCopies) {
        List<BookCopyDto> dtos = new ArrayList<BookCopyDto>();
        for (BookCopy bookCopy : bookCopies) {
            dtos.add(toDto(bookCopy));
        }
        return dtos;
    }
}
