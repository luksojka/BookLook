package com.booklook.booklook.catalog.application;

import com.booklook.booklook.catalog.application.port.AuthorsUseCase;
import com.booklook.booklook.catalog.db.AuthorJpaRepository;
import com.booklook.booklook.catalog.domain.Author;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorsService implements AuthorsUseCase {

    private final AuthorJpaRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
