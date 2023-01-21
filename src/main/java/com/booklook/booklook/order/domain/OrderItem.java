package com.booklook.booklook.order.domain;

import com.booklook.booklook.catalog.domain.Book;
import lombok.Value;

@Value
public class OrderItem {
    Long bookId;
    int quantity;
}
