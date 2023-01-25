package com.booklook.booklook.order.application;

import com.booklook.booklook.order.application.port.ManipulateOrderUseCase;
import com.booklook.booklook.order.db.OrderJpaRepository;
import com.booklook.booklook.order.domain.Order;
import com.booklook.booklook.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ManipulateOrderService implements ManipulateOrderUseCase {

    private final OrderJpaRepository repository;

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

        repository.deleteById(id);
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) {
        repository.findById(id)
                .ifPresent(order ->{
                    order.updateStatus(status);
                    repository.save(order);
                });
    }


}
