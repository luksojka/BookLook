package com.booklook.booklook.order.application;

import com.booklook.booklook.order.application.port.ManipulateOrderUseCase;
import com.booklook.booklook.order.domain.Order;
import com.booklook.booklook.order.domain.OrderRepository;
import com.booklook.booklook.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ManipulateOrderService implements ManipulateOrderUseCase {

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

    @Override
    public void deleteOrderById(Long id) {
        repository.removeById(id);
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) {
        repository.findById(id)
                .ifPresent(order ->{
                    order.setStatus(status);
                    repository.save(order);
                });
    }


}
