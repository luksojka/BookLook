package com.booklook.booklook.catalog.application;

import com.booklook.booklook.catalog.domain.Book;
import com.booklook.booklook.catalog.domain.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService service;

    public List<Book> findByTitle(String title) {
        return service.findByTitle(title);
    }
}
