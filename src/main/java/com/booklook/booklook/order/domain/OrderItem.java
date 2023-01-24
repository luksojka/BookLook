package com.booklook.booklook.order.domain;

import com.booklook.booklook.catalog.domain.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@Entity
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private Long bookId;
    private int quantity;

    public OrderItem(Long bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
