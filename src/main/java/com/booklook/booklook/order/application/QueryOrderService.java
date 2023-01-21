package com.booklook.booklook.order.application;

import com.booklook.booklook.catalog.domain.Book;
import com.booklook.booklook.catalog.domain.CatalogRepository;
import com.booklook.booklook.order.application.port.QueryOrderUseCase;
import com.booklook.booklook.order.domain.Order;
import com.booklook.booklook.order.domain.OrderItem;
import com.booklook.booklook.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderRepository repository;
    private final CatalogRepository catalogRepository;

    @Override
    public List<RichOrder> findAll() {

        return repository.findAll()
                .stream()
                .map(this::toRichOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RichOrder> findById(Long id) {
        return repository.findById(id).map(this::toRichOrder);
    }


    private RichOrder toRichOrder(Order order) {
        List<RichOrderItem> richItems = toRichItems(order.getItems());
        return new RichOrder(
                order.getId(),
                order.getStatus(),
                richItems,
                order.getRecipient(),
                order.getCreatedAt()
        );

    }

    private List<RichOrderItem> toRichItems(List<OrderItem> items) {
        return items.stream()
                .map(item -> {
                    Book book = catalogRepository
                            .findById(item.getBookId())
                            .orElseThrow(()-> new IllegalStateException("Unable to find book with ID: " + item.getBookId()));
                    return new RichOrderItem(book, item.getQuantity());
                })
                .collect(Collectors.toList());
    }
}
