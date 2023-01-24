package com.booklook.booklook.catalog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@RequiredArgsConstructor
@ToString(exclude = "authors")
@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer year;
    private BigDecimal price;
    private Long coverId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    @JsonIgnoreProperties("books")
    private Set<Author> authors;

    public Book(String title, Integer year, BigDecimal price) {
        this.title = title;
        this.year = year;
        this.price = price;
    }
}
