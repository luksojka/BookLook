package com.booklook.booklook.catalog.db;

import com.booklook.booklook.catalog.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.authors")
    List<Book> findAllEager();

    List<Book> findByAuthors_nameContainsIgnoreCase(String name);

    List<Book> findByTitleContainsIgnoreCase(String title);

    Optional<Book> findDistinctFirstByTitleContainsIgnoreCase(String title);

    Optional<Book> findDistinctFirstByAuthors_nameContainsIgnoreCase(String name);

    @Query(" SELECT b FROM Book b JOIN b.authors a " +
            " WHERE " +
            " lower(a.name) LIKE lower(concat('%', :name, '%' )) ")
    List<Book> findByAuthor(@Param("name") String name);

    @Query(" SELECT b FROM Book b JOIN b.authors a " +
            " WHERE " +
            " lower(b.title) LIKE lower(concat('%', :title, '%')) " +
            " AND " +
            " lower(a.name) LIKE lower(concat('%', :name, '%' ))")
    List<Book> findByAuthorAndTitle(@Param("title") String title, @Param("name") String name);

}