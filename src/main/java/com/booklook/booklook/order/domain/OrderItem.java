package com.booklook.booklook.order.domain;

import com.booklook.booklook.catalog.domain.Book;
import com.booklook.booklook.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {

    private Long bookId;
    private int quantity;

}
