package com.booklook.booklook;

import com.booklook.booklook.catalog.application.port.CatalogUseCase;
import com.booklook.booklook.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import com.booklook.booklook.catalog.domain.Book;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase.PlaceOrderResponse;
import com.booklook.booklook.order.application.port.QueryOrderUseCase;
import com.booklook.booklook.order.domain.OrderItem;
import com.booklook.booklook.order.domain.Recipient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final ManipulateOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final String title;
    private final String author;
    private final Long limit;

    public ApplicationStartup(
            CatalogUseCase catalog,
            ManipulateOrderUseCase placeOrder,
            QueryOrderUseCase queryOrder,
            @Value("${bookaro.catalog.query}") String title,
            @Value("${bookaro.catalog.author}") String author,
            @Value("${bookaro.catalog.limit}") Long limit
    ) {
        this.catalog = catalog;
        this.placeOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.title = title;
        this.author = author;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        initData();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        Book karaluchy = catalog.findOneByTitle("Karaluchy").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book wladca = catalog.findOneByTitle("Władca Pierścieni: Powrót Króla").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

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
                .item(new OrderItem(karaluchy.getId(), 16))
                .item(new OrderItem(wladca.getId(),7))
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

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
        findByAuthor();
    }

    private void initData() {
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Karaluchy", "Jo Nesbo", 1998, new BigDecimal("19.90")));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Władca Pierścieni: Powrót Króla", "JRR Tolkien", 1955, new BigDecimal("11.90")));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Pierwszy Śnieg", "Jo Nesbo", 2007, new BigDecimal("29.90")));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("I nie było już nikogo", "Agatha Christie", 1939, new BigDecimal("14.90")));
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        books.forEach(System.out::println);
    }

    private void findByAuthor() {
        List<Book> books = catalog.findByAuthor(author);
        books.forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating book....");
        catalog.findOneByTitleAndAuthor("Karaluchy", "Jo Nesbo")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Karaluchy test")
                            .build();
                    CatalogUseCase.UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }
}
