package com.booklook.booklook.catalog.db;

import com.booklook.booklook.catalog.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
}
