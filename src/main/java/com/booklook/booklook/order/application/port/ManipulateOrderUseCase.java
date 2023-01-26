package com.booklook.booklook.order.application.port;

import com.booklook.booklook.commons.Either;
import com.booklook.booklook.order.domain.OrderItem;
import com.booklook.booklook.order.domain.OrderStatus;
import com.booklook.booklook.order.domain.Recipient;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.Collections.*;

public interface ManipulateOrderUseCase {
    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    void deleteOrderById(Long id);

    void updateOrderStatus(Long id, OrderStatus status);

    @Builder
    @Value
    @AllArgsConstructor
    class PlaceOrderCommand {
        @Singular
        Set<OrderItemCommand> items;
        Recipient recipient;
    }

    @Value
    static class OrderItemCommand {
        Long bookId;
        int quantity;
    }

    @Value
    class PlaceOrderResponse extends Either<String, Long> {
        public PlaceOrderResponse(boolean success, String left, Long right) {
            super(success,left, right);
        }

        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, null, orderId);
        }

        public static PlaceOrderResponse failure(String error) {
            return new PlaceOrderResponse(false, error, null);
        }
    }
}
