package com.booklook.booklook.catalog.application;


import com.booklook.booklook.catalog.application.port.CatalogUseCase;
import com.booklook.booklook.catalog.db.AuthorJpaRepository;
import com.booklook.booklook.catalog.db.BookJpaRepository;
import com.booklook.booklook.catalog.domain.Author;
import com.booklook.booklook.catalog.domain.Book;
import com.booklook.booklook.uploads.application.port.UploadUseCase;
import com.booklook.booklook.uploads.application.port.UploadUseCase.SaveUploadCommand;
import com.booklook.booklook.uploads.domain.Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class CatalogService implements CatalogUseCase {

    private final BookJpaRepository repository;
    private final AuthorJpaRepository authorRepository;
    private final UploadUseCase upload;

    @Override
    public List<Book> findAll() {
        return repository.findAllEager();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return repository.findByTitleContainsIgnoreCase(title);
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return repository.findDistinctFirstByTitleContainsIgnoreCase(title);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findByAuthors_nameContainsIgnoreCase(author);
    }

    @Override
    public Optional<Book> findOneByAuthor(String author) {
        return repository.findDistinctFirstByAuthors_nameContainsIgnoreCase(author);
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return repository.findByAuthorAndTitle(title, author);
    }

    @Override
    @Transactional
    public Book addBook(CreateBookCommand command) {
        Book book = toBook(command);
        return repository.save(book);
    }

    private Book toBook(CreateBookCommand command) {
        Book book = new Book(command.getTitle(), command.getYear(), command.getPrice(), command.getAvailable());
        Set<Author> authors = fetchAuthorsByIds(command.getAuthors());
        updateBooks(book, authors);
        return book;

    }

    private void updateBooks(Book book, Set<Author> authors) {
        book.removeAuthors();
        authors.forEach(author -> book.addAuthor(author));
    }

    private Set<Author> fetchAuthorsByIds(Set<Long> authors) {
        return authors
                .stream()
                .map(authorId -> authorRepository
                        .findById(authorId)
                        .orElseThrow(() -> new IllegalArgumentException("Unable to find author with id: " + authorId))
                )
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        return repository
                .findById(command.getId())
                .map(book -> {
                    Book updatedBook = updateFields(command, book);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateBookResponse(false, Collections.singletonList("Book not found with id: " + command.getId())));
    }

    private Book updateFields(UpdateBookCommand command, Book book) {
        if (command.getTitle() != null) {
            book.setTitle(command.getTitle());
        }
        if (command.getAuthors() != null && command.getAuthors().size() > 0) {
            updateBooks(book, fetchAuthorsByIds(command.getAuthors()));
        }
        if (command.getYear() != null) {
            book.setYear(command.getYear());
        }
        if (command.getPrice() != null) {
            book.setPrice(command.getPrice());
        }
        return book;

    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateBookCover(UpdateBookCoverCommand command) {
        repository.findById(command.getId())
                .ifPresent(book -> {
                    Upload savedUpload = upload.save(new SaveUploadCommand(command.getFilename(), command.getFile(), command.getContentType()));
                    book.setCoverId(savedUpload.getId());
                    repository.save(book);
                });
    }

    @Override
    public void removeBookCover(Long id) {
        repository.findById(id)
                .ifPresent(book -> {
                    if (book.getCoverId() != null) {
                        upload.removeById(book.getCoverId());
                        book.setCoverId(null);
                        repository.save(book);
                    }
                });
    }
}
