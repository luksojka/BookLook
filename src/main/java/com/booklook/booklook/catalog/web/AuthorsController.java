package com.booklook.booklook.catalog.web;

import com.booklook.booklook.catalog.application.port.AuthorsUseCase;
import com.booklook.booklook.catalog.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorsUseCase authors;

    @GetMapping
    public List<Author> findAll() {
        return authors.findAll();
    }
}
