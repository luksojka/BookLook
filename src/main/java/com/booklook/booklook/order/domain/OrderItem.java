package com.booklook.booklook.order.domain;

import com.booklook.booklook.catalog.domain.Book;
import com.booklook.booklook.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private int quantity;

}
