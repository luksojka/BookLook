package com.booklook.booklook.order.application;

import com.booklook.booklook.order.application.port.ManipulateOrderUseCase;
import com.booklook.booklook.order.db.OrderJpaRepository;
import com.booklook.booklook.order.domain.Order;
import com.booklook.booklook.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class AbandonedOrdersJob {
    private final OrderJpaRepository repository;
    private final ManipulateOrderUseCase orderUseCase;

    @Scheduled(fixedRate = 60_000)
    public void run() {
        LocalDateTime timestamp = LocalDateTime.now().minusMinutes(5);
        List<Order> orders = repository.findByStatusAndCreatedAtLessThanEqual(OrderStatus.NEW, timestamp);
        System.out.println("Found orders to be abandoned: " + orders.size());
        orders.forEach(order -> orderUseCase.updateOrderStatus(order.getId(), OrderStatus.ABANDONED));
    }
}
