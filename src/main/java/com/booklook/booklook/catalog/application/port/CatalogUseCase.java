package com.booklook.booklook.catalog.application.port;

import com.booklook.booklook.catalog.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CatalogUseCase {
    List<Book> findByTitle(String title);

    Optional<Book> findById(Long id);

    Optional<Book> findOneByTitle(String title);

    Optional<Book> findOneByAuthor(String author);

    List<Book> findByAuthor(String author);

    List<Book> findAll();

    List<Book> findByTitleAndAuthor(String title, String author);

    Book addBook(CreateBookCommand command);

    void removeById(Long id);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    void updateBookCover(UpdateBookCoverCommand command);

    void removeBookCover(Long id);

    @Value
    class UpdateBookCoverCommand {
        Long id;
        byte[] file;
        String contentType;
        String filename;
    }

    @Value
    class CreateBookCommand {
        String title;
        Set<Long> authors;
        Integer year;
        BigDecimal price;
        Long available;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class UpdateBookCommand {
        Long id;
        String title;
        Set<Long> authors;
        Integer year;
        BigDecimal price;
    }

    @Value
    class UpdateBookResponse {
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }
}
