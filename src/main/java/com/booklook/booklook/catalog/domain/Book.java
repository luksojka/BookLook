package com.booklook.booklook.catalog.domain;

import com.booklook.booklook.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@ToString(exclude = "authors")
@Getter
@Setter
@Entity
public class Book extends BaseEntity {

    private String title;
    private Integer year;
    private BigDecimal price;
    private Long coverId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
    @JsonIgnoreProperties("books")
    private Set<Author> authors = new HashSet<>();

    public Book(String title, Integer year, BigDecimal price) {
        this.title = title;
        this.year = year;
        this.price = price;
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getBooks().remove(this);
    }

    public void removeAuthors() {
        Book self = this;
        authors.forEach(author -> author.getBooks().remove(self));
        authors.clear();
    }
}
