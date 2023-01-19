package com.booklook.booklook;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BooklookOnlineStoreApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(BooklookOnlineStoreApplication.class, args);
    }
    private final CatalogService catalogService;

    public BooklookOnlineStoreApplication(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public void run(String... args) {
        List<Book> books = catalogService.findByTitle("Pan Tadeusz");
        books.forEach(System.out::println);
    }
}
