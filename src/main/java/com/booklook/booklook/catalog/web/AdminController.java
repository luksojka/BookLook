package com.booklook.booklook.catalog.web;

import com.booklook.booklook.catalog.application.port.CatalogInitializerUseCase;
import com.booklook.booklook.catalog.application.port.CatalogUseCase;
import com.booklook.booklook.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.booklook.booklook.catalog.db.AuthorJpaRepository;
import com.booklook.booklook.catalog.domain.Author;
import com.booklook.booklook.catalog.domain.Book;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase.OrderItemCommand;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import com.booklook.booklook.order.application.port.QueryOrderUseCase;
import com.booklook.booklook.order.domain.OrderItem;
import com.booklook.booklook.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@Slf4j
public class AdminController {

    private final CatalogInitializerUseCase initializer;

    @PostMapping("/initialization")
    @Transactional
    public void initialize(){
        initializer.initialize();
    }


}
