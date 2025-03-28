package ba.unsa.etf.book_service.mappers;

import ba.unsa.etf.book_service.book_service.dtos.BookDto;
import ba.unsa.etf.book_service.book_service.models.Book;

public class BookMapper {
        public static BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setPublishedYear(book.getPublishedYear());
        return dto;
    }
}
