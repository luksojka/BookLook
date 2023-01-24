package com.booklook.booklook.catalog.db;

import com.booklook.booklook.catalog.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthors_firstNameContainsIgnoreCaseOrAuthors_lastNameContainsIgnoreCase(String firstName, String lastName);

    List<Book> findByTitleContainsIgnoreCase(String title);

    Optional<Book> findDistinctFirstByTitleContainsIgnoreCase(String title);

    Optional<Book> findDistinctFirstByAuthors_firstNameContainsIgnoreCaseOrAuthors_lastNameContainsIgnoreCase(String firstName, String lastName);

    @Query(" SELECT b FROM Book b JOIN b.authors a " +
            " WHERE " +
            " lower(a.firstName) LIKE lower(concat('%', :name, '%' )) " +
            " OR lower(a.lastName) LIKE lower(concat('%', :name, '%' )) ")
    List<Book> findByAuthor(@Param("name") String name);

    @Query(" SELECT b FROM Book b JOIN b.authors a " +
            " WHERE " +
            " lower(b.title) LIKE lower(concat('%', :title, '%')) " +
            " AND " +
            " (lower(a.firstName) LIKE lower(concat('%', :name, '%' ))) ")
    List<Book> findByAuthorAndTitle(@Param("name") String name, @Param("title") String title);

}