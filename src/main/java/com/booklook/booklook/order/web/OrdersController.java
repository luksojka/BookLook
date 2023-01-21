package com.booklook.booklook.order.web;

import com.booklook.booklook.order.application.port.ManipulateOrderUseCase;
import com.booklook.booklook.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import com.booklook.booklook.order.application.port.QueryOrderUseCase;
import com.booklook.booklook.order.application.port.QueryOrderUseCase.RichOrder;
import com.booklook.booklook.order.domain.OrderItem;
import com.booklook.booklook.order.domain.OrderStatus;
import com.booklook.booklook.order.domain.Recipient;
import com.booklook.booklook.web.CreatedURI;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;



@RequestMapping("/orders")
@RestController
@AllArgsConstructor
class OrdersController {

    private final ManipulateOrderUseCase manipulateOrder;
    private final QueryOrderUseCase queryOrder;

    @GetMapping
    public List<RichOrder> getOrders() {
        return queryOrder.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RichOrder> getOrderById(@PathVariable Long id) {
        return queryOrder
                .findById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createOrder(@RequestBody CreateOrderCommand command) {
        return manipulateOrder
                .placeOrder(command.toPlaceOrderCommand())
                .handle(
                        orderId -> ResponseEntity.created(orderUri(orderId)).build(),
                        error -> ResponseEntity.badRequest().body(error)
                );
    }

    URI orderUri(Long orderId) {
        return new CreatedURI("/" + orderId).uri();
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrderStatus(@PathVariable Long id, @RequestBody UpdateStatusCommand command) {
        OrderStatus orderStatus = OrderStatus
                .parseString(command.status)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknow status: " + command.status));
        manipulateOrder.updateOrderStatus(id, orderStatus);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        manipulateOrder.deleteOrderById(id);
    }

    @Data
    private static class CreateOrderCommand {
        List<OrderItemCommand> items;
        RecipientCommand recipient;

        PlaceOrderCommand toPlaceOrderCommand() {
            List<OrderItem> orderItems = items
                    .stream()
                    .map(item -> new OrderItem(item.bookId, item.quantity))
                    .collect(Collectors.toList());
            return new PlaceOrderCommand(orderItems, recipient.toRecipient());
        }
    }

    @Data
    private static class OrderItemCommand {
        Long bookId;
        int quantity;
    }

    @Data
    private static class RecipientCommand {
        String name;
        String phone;
        String city;
        String street;
        String zipCode;
        String email;

        Recipient toRecipient() {
            return new Recipient(name, phone, street, city, zipCode, email);
        }
    }

    @Data
    static class UpdateStatusCommand {
        String status;
    }

}
