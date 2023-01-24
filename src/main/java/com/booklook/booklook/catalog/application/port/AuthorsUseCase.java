package com.booklook.booklook.catalog.application.port;

import com.booklook.booklook.catalog.domain.Author;

import java.util.List;

public interface AuthorsUseCase {
    List<Author> findAll();
}
