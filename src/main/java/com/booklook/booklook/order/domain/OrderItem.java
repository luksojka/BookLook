package com.booklook.booklook.order.domain;

import com.booklook.booklook.catalog.domain.Book;
import lombok.Value;

@Value
public class OrderItem {
    Book book;
    int quantity;
}
