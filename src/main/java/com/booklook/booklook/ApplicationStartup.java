package com.booklook.booklook;

import com.booklook.booklook.catalog.application.port.CatalogUseCase;
import com.booklook.booklook.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.booklook.booklook.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import com.booklook.booklook.catalog.db.AuthorJpaRepository;
import com.booklook.booklook.catalog.domain.Author;
import com.booklook.booklook.catalog.domain.Book;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase.PlaceOrderResponse;
import com.booklook.booklook.order.application.port.QueryOrderUseCase;
import com.booklook.booklook.order.domain.OrderItem;
import com.booklook.booklook.order.domain.Recipient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final ManipulateOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final AuthorJpaRepository authorRepository;

    @Override
    public void run(String... args) {
        initData();
        placeOrder();
    }

    private void placeOrder() {
        Book effectiveJava = catalog.findOneByTitle("Effective Java").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book puzzlers = catalog.findOneByTitle("Java Puzzlers").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        Recipient recipient = Recipient
                .builder()
                .name("Jan")
                .phone("Kowalski")
                .city("Kraków")
                .street("Armii Krajowej 31")
                .zipCode("00-123")
                .email("jan@example.org")
                .build();

        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(effectiveJava.getId(), 16))
                .item(new OrderItem(puzzlers.getId(), 7))
                .build();

        PlaceOrderResponse response = placeOrder.placeOrder(command);

        String result = response.handle(
                orderId -> "Created ORDER with id: " + orderId,
                error -> "Failed to create order: " + error
        );

        System.out.println(result);

        queryOrder.findAll()
                .forEach(order -> System.out.println("GOT ORDER WITH TOTAL PRICE" + order.totalPrice() + " DETAILS: " + order));

    }

    private void initData() {
        Author joshua = new Author("Joshua", "Bloch");
        Author neal = new Author("Neal", "Gafter");

        authorRepository.save(joshua);
        authorRepository.save(neal);

        CreateBookCommand effectiveJava = new CreateBookCommand(
                "Effective Java",
                Set.of(joshua.getId()),
                2005,
                new BigDecimal("79.00")
        );

        CreateBookCommand javaPuzzlers = new CreateBookCommand(
                "Java Puzzlers",
                Set.of(joshua.getId(), neal.getId()),
                2018,
                new BigDecimal("99.00")
        );

        catalog.addBook(javaPuzzlers);
        catalog.addBook(effectiveJava);

    }
}
