package com.booklook.booklook.order.application;

import com.booklook.booklook.order.application.port.PlaceOrderUseCase;
import com.booklook.booklook.order.domain.Order;
import com.booklook.booklook.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class PlaceOrderService implements PlaceOrderUseCase {

    private final OrderRepository repository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order
                .builder()
                .recipient(command.getRecipient())
                .items(command.getItems())
                .build();

        Order save = repository.save(order);
        return PlaceOrderResponse.success(save.getId());
    }
}
