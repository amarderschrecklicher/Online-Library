package ba.unsa.etf.book_service.book_service.mappers;

import ba.unsa.etf.book_service.book_service.dtos.BookCopyDto;
import ba.unsa.etf.book_service.book_service.models.BookCopy;

public class BookCopyMapper {

    public static BookCopyDto toDto(BookCopy copy) {
        BookCopyDto dto = new BookCopyDto();
        dto.setId(copy.getId());
        dto.setCode(copy.getCode());
        dto.setStatus(copy.getStatus());
        dto.setBookId(copy.getBook().getId());
        return dto;
    }    
    
}
