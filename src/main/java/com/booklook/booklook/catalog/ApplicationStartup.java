package com.booklook.booklook.catalog;

import com.booklook.booklook.catalog.application.port.CatalogUseCase;
import com.booklook.booklook.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import com.booklook.booklook.catalog.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String title;
    private final String author;
    private final Long limit;

    public ApplicationStartup(
            CatalogUseCase catalog,
            @Value("${bookaro.catalog.query}") String title,
            @Value("${bookaro.catalog.author}") String author,
            @Value("${bookaro.catalog.limit}") Long limit
    ) {
        this.catalog = catalog;
        this.title = title;
        this.author = author;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        initData();
        findByTitle();
        findAndUpdate();
        findByTitle();
        findByAuthor();
    }

    private void initData() {
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Karaluchy", "Jo Nesbo", 1998));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Władca Pierścieni: Powrót Króla", "JRR Tolkien", 1955));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Pierwszy Śnieg", "Jo Nesbo", 2007));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("I nie było już nikogo", "Agatha Christie", 1939));
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        books.forEach(System.out::println);
    }

    private void findByAuthor() {
        List<Book> books = catalog.findByAuthor(author);
        books.forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating book....");
        catalog.findOneByTitleAndAuthor("Karaluchy", "Jo Nesbo")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Karaluchy test")
                            .build();
                    CatalogUseCase.UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }
}
