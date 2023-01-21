package com.booklook.booklook.catalog.web;

import com.booklook.booklook.catalog.application.port.CatalogUseCase;
import com.booklook.booklook.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.booklook.booklook.catalog.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
class CatalogController {

    private final CatalogUseCase catalog;

    @GetMapping
    public List<Book> getAll(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> author) {
        if (title.isPresent() && author.isPresent()) {
            return catalog.findByTitleAndAuthor(title.get(), author.get());
        } else if (title.isPresent()) {
            return catalog.findByTitle(title.get());
        } else if (author.isPresent()) {
            return catalog.findByAuthor(author.get());
        }
        return catalog.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return catalog
                .findById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody RestCreateBookCommand command) {
        Book book = catalog.addBook(command.toCommand());
        return book;
    }

    @Data
    private static class RestCreateBookCommand {
        private String title;
        private String author;
        private Integer year;
        private BigDecimal price;

        CreateBookCommand toCommand() {
            return new CreateBookCommand(title, author, year, price);
        }
    }
}