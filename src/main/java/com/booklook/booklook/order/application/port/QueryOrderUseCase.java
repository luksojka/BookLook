package com.booklook.booklook.order.application.port;

import com.booklook.booklook.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {
    List<Order> findAll();
}
