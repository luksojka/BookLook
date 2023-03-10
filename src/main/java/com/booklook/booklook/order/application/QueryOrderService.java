package com.booklook.booklook.order.application;

import com.booklook.booklook.catalog.db.BookJpaRepository;
import com.booklook.booklook.order.application.port.QueryOrderUseCase;
import com.booklook.booklook.order.db.OrderJpaRepository;
import com.booklook.booklook.order.domain.Order;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderJpaRepository repository;
    private final BookJpaRepository catalogRepository;

    @Override
    @Transactional
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
        return new RichOrder(
                order.getId(),
                order.getStatus(),
                order.getItems(),
                order.getRecipient(),
                order.getCreatedAt()
        );

    }

}
