package com.booklook.booklook;

import com.booklook.booklook.catalog.application.CatalogController;
import com.booklook.booklook.catalog.domain.Book;
import com.booklook.booklook.catalog.domain.CatalogService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BooklookOnlineStoreApplication {



    public static void main(String[] args) {
        SpringApplication.run(BooklookOnlineStoreApplication.class, args);
    }

}
